package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.OperateLogService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupMemberReduce;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMemberReduce;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;

/**
 * 监听群友减少事件
 */
@Slf4j
@Component
public class GroupMemberReduceListener extends BaseListener {

    private final OperateLogService operateLogService;
    private final MemberService memberService;
    private final RosterService rosterService;

    @Autowired
    public GroupMemberReduceListener(MessageContentBuilderFactory builderFactory, OperateLogService operateLogService
            , MemberService memberService, RosterService rosterService) {
        super(builderFactory);
        this.operateLogService = operateLogService;
        this.memberService = memberService;
        this.rosterService = rosterService;
    }

    /**
     * 群成员减少
     */
    @Transactional(rollbackFor = Exception.class)
    @OnGroupMemberReduce
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
            CustomerFilter.FORMAL_GROUP})
    public void groupMemberReduceListener(GroupMemberReduce reduceMsg, MsgSender sender) {
        log.info("群成员减一");
        // 保存群成员变更记录
        OperateLog operateLog = buildOperateLog(String.valueOf(reduceMsg.getReduceType()), reduceMsg);
        operateLogService.save(operateLog);

        // 更新群员信息
        Member member = memberService.findByGroupCodeAndQqCode(reduceMsg.getGroupInfo().getGroupCodeNumber(),
                reduceMsg.getBeOperatorInfo().getBeOperatorCodeNumber());
        if (member != null) {
            member.setStatus(reduceMsg.getReduceType() == GroupMemberReduce.Type.KICK ? Member.Status.KICK :
                    Member.Status.LEAVE);
            memberService.save(member);
        }
        // 若成员是被踢出，加黑名单
        if (reduceMsg.getReduceType() == GroupMemberReduce.Type.KICK) {
            Roster roster = new Roster();
            roster.setMemberCode(reduceMsg.getAccountInfo().getAccountCodeNumber());
            roster.setType(Roster.Type.BLACK_ROSTER);
            rosterService.save(roster);
        }
    }
}
