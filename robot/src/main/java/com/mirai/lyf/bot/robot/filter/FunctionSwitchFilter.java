package com.mirai.lyf.bot.robot.filter;

import com.mirai.lyf.bot.common.utils.StringUtils;
import com.mirai.lyf.bot.persistence.domain.system.SysMenu;
import com.mirai.lyf.bot.persistence.domain.system.SysMenuGroupInfo;
import com.mirai.lyf.bot.persistence.repository.system.MenuGroupInfoRepository;
import com.mirai.lyf.bot.persistence.service.system.MenuService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.containers.GroupContainer;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class FunctionSwitchFilter implements ListenerFilter {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuGroupInfoRepository menuGroupInfoRepository;

    @Override
    public boolean test(@NotNull FilterData data) {
        MsgGet msgGet = data.getMsgGet();
        String msg = msgGet.getText();
        if (StringUtils.isEmpty(msg)) {
            msg = "";
        }
        if (msgGet instanceof GroupContainer) {
            if (msg.trim().equals("#菜单")) {
                return Boolean.TRUE;
            }
            GroupContainer groupContainer = (GroupContainer) msgGet;
            String groupCode = groupContainer.getGroupInfo().getGroupCode();
            List<SysMenu> all = menuService.findAll();
            for (SysMenu sysMenu : all) {
                if (msg.startsWith(sysMenu.getName())) {
                    SysMenuGroupInfo byCodeAndAndGroupCode = menuGroupInfoRepository.findByCodeAndAndGroupCode(sysMenu.getCode(), groupCode);
                    return Optional.ofNullable(byCodeAndAndGroupCode).map(SysMenuGroupInfo::getStatus).orElse(Boolean.FALSE);
                }
            }
        }
        if (msgGet instanceof PrivateMsg) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
