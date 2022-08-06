package com.mirai.lyf.bot.robot.filter;


import com.mirai.lyf.bot.persistence.domain.system.SysMenu;
import com.mirai.lyf.bot.persistence.domain.system.SysMenuGroupInfo;
import com.mirai.lyf.bot.persistence.repository.system.MenuGroupInfoRepository;
import com.mirai.lyf.bot.persistence.service.system.MenuService;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.definition.Group;
import love.forte.simbot.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.Optional;

/**
 * 功能开关
 *
 * @author LYF.UAENA
 * @since 2022年03月27日 10:59
 */
@Slf4j
@Component("functionSwitchFilter")
public class FunctionSwitchFilter implements BlockingEventProcessingInterceptor {
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuGroupInfoRepository menuGroupInfoRepository;

    @NotNull
    @Override
    public EventProcessingResult doIntercept(@NotNull Context context) {
        // 事件ID，用于日志
        final Event event = context.getEventContext().getEvent();

//        if (event i
        if (event instanceof ChangeEvent) {
            return context.proceedBlocking();
        }
        if (event instanceof ChangedEvent) {
            return context.proceedBlocking();
        }
        if (event instanceof GroupMessageEvent) {
            String plainText = ((GroupMessageEvent) event).getMessageContent().getPlainText();
            String trim = plainText.trim();
            if (!trim.startsWith("#")) {
                return context.proceedBlocking();
            }
            if (trim.equals("#菜单") || trim.startsWith("#开启") || trim.startsWith("#关闭")) {
                return context.proceedBlocking();
            }
            Group group = ((GroupMessageEvent) event).getGroup();
            String id = group.getId().toString();

            List<SysMenu> all = menuService.findAll();
            for (SysMenu sysMenu : all) {
                if (plainText.startsWith(sysMenu.getName())) {
                    SysMenuGroupInfo byCodeAndAndGroupCode = menuGroupInfoRepository.findByCodeAndAndGroupCode(sysMenu.getCode(), id);
                    Boolean orElse = Optional.ofNullable(byCodeAndAndGroupCode).map(SysMenuGroupInfo::getStatus).orElse(Boolean.FALSE);
                    if (orElse) {
                        return context.proceedBlocking();
                    }
                }
            }
        }
        log.info("阻止执行了");
        return EventProcessingResult.Empty;
    }
}
