package com.mirai.lyf.bot.robot.listener.group;

import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * 监听群聊消息
 */
@Slf4j
@Component
public class GroupListener1 {

    /**
     * 菜单
     *
     * @param messageEvent the message event
     */
    @Listener
    @Filter(value = "#菜单", matchType = MatchType.TEXT_STARTS_WITH)
    public void menu(GroupMessageEvent messageEvent) {
        messageEvent.getGroup().sendBlocking("Hi");
    }
}
