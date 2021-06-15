package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.utils.PatternUtil;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupAddRequest;
import love.forte.simbot.api.message.events.GroupAddRequest;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听群添加请求事件
 */
@Slf4j
@Component
public class GroupAddRequestListener {

    private final RosterService rosterService;

    @Autowired
    public GroupAddRequestListener(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    /**
     * 群添加申请事件
     *
     * @param requestMsg the request msg
     * @param sender     the sender
     * @param bot        the bot
     */
    @OnGroupAddRequest
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.VERIFY_BOT,
            CustomerFilter.FORMAL_GROUP})
    public void groupAddRequestListener(GroupAddRequest requestMsg, MsgSender sender, Bot bot) {
        log.info(
                "add request from: {}, message: {}, request type: {}",
                requestMsg.getAccountInfo().getAccountCode(),
                requestMsg.getText(),
                requestMsg.getRequestType()
        );

        String pattern = "netbian|NETBIAN";
        // 主动加群
        if (GroupAddRequest.Type.PROACTIVE == requestMsg.getRequestType()) {
            boolean allowRequest = true;
            Roster roster = rosterService.findByMemberCode(requestMsg.getAccountInfo().getAccountCodeNumber());
            if (roster != null && roster.getType() == Roster.Type.BLACK_ROSTER) {
                allowRequest = false;
            }

            if (!allowRequest) {
                sender.SETTER.setGroupAddRequest(requestMsg.getFlag(), false, false, "您曾经被踢出群聊，不允许重新加群！");
            } else {
                sender.SETTER.setGroupAddRequest(requestMsg.getFlag(), PatternUtil.stringPattern(pattern,
                        requestMsg.getText()), false, "请正确回答问题哦！如果不知道正确答案的话，请百度搜索‘彼岸图网’");
            }
        } else {
            // 提示通过群号搜索添加
            sender.SETTER.setGroupAddRequest(requestMsg.getFlag(), false, false, "不可通过邀请方式进群，请通过群号搜索添加");
        }
    }
}
