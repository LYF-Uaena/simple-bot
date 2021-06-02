package com.mirai.lyf.bot.robot.filter;

import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.containers.GroupContainer;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LYF
 * @create 2021-03-26 14:45
 * @desc 黑白名单功能消息过滤器
 **/
@Slf4j
@Component("rosterFilter")
public class RosterFilter extends BaseFilter implements ListenerFilter {

    @Autowired
    public RosterFilter(ConfigService configService) {
        super(configService);
    }

    @Override
    public boolean test(@NotNull FilterData data) {
        Config config = configService.find(ConfigCodeKit.FORMAL_GROUP);
        MsgGet msgGet = data.getMsgGet();
        String code = "";

        // 获取当前消息群号码
        if (msgGet instanceof GroupContainer) {
            GroupContainer groupContainer = (GroupContainer) msgGet;
            code = groupContainer.getGroupInfo().getGroupCode();
        }
        return compare(config, code);
    }

}
