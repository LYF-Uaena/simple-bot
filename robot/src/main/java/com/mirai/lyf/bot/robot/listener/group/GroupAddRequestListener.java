package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.utils.PatternUtil;
import com.mirai.lyf.bot.common.utils.StringUtils;
import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.ID;
import love.forte.simbot.component.mirai.event.MiraiMemberJoinRequestEvent;
import love.forte.simbot.definition.UserInfo;
import love.forte.simbot.event.RequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听群添加请求事件
 */
@Slf4j
@Component
public class GroupAddRequestListener {

    @Autowired
    private RosterService rosterService;
    @Autowired
    private MemberService memberService;

    /**
     * 群添加申请事件
     */
    @Listener
    public void groupAddRequestListener(MiraiMemberJoinRequestEvent event) {
        // 申请类型
        RequestEvent.Type type = event.getType();
        ID id = event.getRequester().getId();
        String message = event.getMessage();
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
                if (PatternUtil.getGroupAddInstance().matcher(message).find()) {
                    event.acceptAsync();
                } else {
                    event.rejectAsync("请正确回答问题哦！如果不知道正确答案的话，请百度搜索‘彼岸图网’");
                }
                return;
            } else {
                event.rejectAsync("您曾经被踢出群聊，不允许重新加群！");
            }
        }
        // 被邀请
        if (RequestEvent.Type.INVITATION.compareTo(type) == 0) {
            UserInfo inviter = event.getInviter();
            if (inviter != null) {
                ID inviterId = inviter.getId();
                MemberInfo memberInfo = memberService.findByGroupCodeAndQqCode(event.getRequester().getFromGroupId(), Long.valueOf(inviterId.toString()));
                if (StringUtils.equals("MEMBER", memberInfo.getIdentity())) {
                    event.acceptAsync();
                }
            }
            event.rejectAsync("不可通过邀请方式进群，请通过群号搜索添加");
        }
    }
}
