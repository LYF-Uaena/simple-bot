package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.utils.PatternUtil;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.ID;
import love.forte.simbot.definition.UserInfo;
import love.forte.simbot.event.GroupJoinRequestEvent;
import love.forte.simbot.event.GroupRequestEvent;
import love.forte.simbot.event.RequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 监听群添加请求事件
 */
@Slf4j
@Component
public class GroupAddRequestListener {

    @Autowired
    private RosterService rosterService;

    /**
     * 群添加申请事件
     *
     * @param requestEvent the request event
     */
    @Listener
    public void groupAddRequestListener(GroupRequestEvent requestEvent) {
        // 申请类型
        RequestEvent.Type type = requestEvent.getType();
        ID id = requestEvent.getRequester().getId();
        String message = requestEvent.getMessage();
        log.info("add request from: {}, message: {}, request type: {}", id, message, type);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("sleep异常");
        }

        // 主动加群
        if (RequestEvent.Type.APPLICATION.compareTo(type) == 0) {
            boolean allowRequest = true;
            Roster roster = rosterService.findByMemberCode(Long.parseLong(id.toString()));
            if (roster != null && roster.getType() == Roster.Type.BLACK_ROSTER) {
                allowRequest = false;
            }
            if (allowRequest) {
                String text = Optional.ofNullable(message).orElse("");
                if (PatternUtil.getGroupAddInstance().matcher(text).find()) {
                    requestEvent.acceptAsync();
                    return;
                } else {
                    requestEvent.rejectAsync();
                    return;
                }
//                sender.SETTER.setGroupAddRequest(
//                        requestMsg.getFlag(),
//                        PatternUtil.getGroupAddInstance().matcher(text).find(),
//                        false,
//                        "请正确回答问题哦！如果不知道正确答案的话，请百度搜索‘彼岸图网’"
//                );
            } else {
                requestEvent.rejectAsync();
//                sender.SETTER.setGroupAddRequest(requestMsg.getFlag(), false, false, "您曾经被踢出群聊，不允许重新加群！");
            }
        }
        // 被邀请
        if (RequestEvent.Type.INVITATION.compareTo(type) == 0 && requestEvent instanceof GroupJoinRequestEvent) {
            UserInfo inviter = ((GroupJoinRequestEvent) requestEvent).getInviter();
            requestEvent.rejectAsync();
        }
//
//        else {
//            // 提示通过群号搜索添加
//            sender.SETTER.setGroupAddRequest(requestMsg.getFlag(), false, false, "不可通过邀请方式进群，请通过群号搜索添加");
//        }
    }
}
