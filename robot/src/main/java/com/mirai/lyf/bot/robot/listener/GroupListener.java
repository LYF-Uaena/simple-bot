package com.mirai.lyf.bot.robot.listener;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.utils.DateUtils;
import com.mirai.lyf.bot.common.utils.PatternUtil;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.MemberMessage;
import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.ImageService;
import com.mirai.lyf.bot.persistence.service.master.*;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.*;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.*;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LYF
 */
@Slf4j
@Component
public class GroupListener extends BaseListener {
    private final MemberService memberService;
    private final OperateLogService operateLogService;
    private final ImageService imageService;
    private final ImageLogService imageLogService;
    private final MemberMessageService memberMessageService;
    private final RosterService rosterService;


    @Autowired
    public GroupListener(MemberService memberService, OperateLogService operateLogService,
                         ImageService imageService, ImageLogService imageLogService,
                         MessageContentBuilderFactory builderFactory, MemberMessageService memberMessageService,
                         RosterService rosterService) {
        super(builderFactory);
        this.memberService = memberService;
        this.operateLogService = operateLogService;
        this.imageService = imageService;
        this.imageLogService = imageLogService;
        this.memberMessageService = memberMessageService;
        this.rosterService = rosterService;
    }


    /**
     * 群成员减少
     */
    @Transactional(rollbackFor = Exception.class)
    @OnGroupMemberReduce
//    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
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

        // 若成员是被踢出，加黑名单
        if (reduceMsg.getReduceType() == GroupMemberReduce.Type.KICK) {
            member.setStatus(Member.Status.KICK);
            Roster roster = new Roster();
            roster.setMemberCode(member.getId());
            roster.setType(Roster.Type.BLACK_ROSTER);
            rosterService.save(roster);
        } else {
            member.setStatus(Member.Status.LEAVE);
        }
        memberService.save(member);
    }

    /**
     * 群成员增加
     */
    @OnGroupMemberIncrease
//    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
            CustomerFilter.FORMAL_GROUP})
    public void groupMemberIncreaseListener(GroupMemberIncrease increaseMsg, MsgSender sender) {
        log.info("新增了一名群成员");
        long groupCode = increaseMsg.getGroupInfo().getGroupCodeNumber();
        long beOperatorCode = increaseMsg.getBeOperatorInfo().getBeOperatorCodeNumber();

        // 发送欢迎新成员的信息
        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
        MessageContent messageContent = builder.at(increaseMsg.getAccountInfo().getAccountCode()).text("天青色等烟雨 " +
                "而我在等你。欢迎加入群聊！").build();
        sender.SENDER.sendGroupMsg(groupCode, messageContent);

        // 保存操作记录
        OperateLog operateLog = buildOperateLog(String.valueOf(increaseMsg.getIncreaseType()), increaseMsg);
        operateLogService.save(operateLog);

        // 保存新成员信息
        GroupMemberInfo memberInfo = sender.GETTER.getMemberInfo(groupCode, beOperatorCode);
        Member member = memberService.findByGroupCodeAndQqCode(groupCode, beOperatorCode);
        // 若不存在，新增，存在则不处理
        if (member == null) {
            member = buildMember(memberInfo);
            member.setLastSpeakTime(DateUtils.now());
            memberService.save(member);
        }
    }

    /**
     * 群成员禁言
     *
     * @param muteMsg the mute msg
     * @param sender  the sender
     * @return void
     */
    @OnGroupMute
    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
    public void groupMuteListener(GroupMute muteMsg, MsgSender sender) {
        log.info("accountCode = " + muteMsg.getAccountInfo().getAccountCode());
        // 保存操作记录
        OperateLog operateLog = buildOperateLog(String.valueOf(muteMsg.getMuteActionType()), muteMsg);
        operateLogService.save(operateLog);
    }

    /**
     * 群添加申请事件
     *
     * @param requestMsg the request msg
     * @param sender     the sender
     * @param bot        the bot
     */
    @OnGroupAddRequest
//    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.VERIFY_BOT,
            CustomerFilter.FORMAL_GROUP})
    public void groupAddRequestListener(GroupAddRequest requestMsg, MsgSender sender, Bot bot) {
        log.info("add request from: " + requestMsg.getAccountInfo().getAccountCode() + ", message: " + requestMsg.getText() + " request type: " + requestMsg.getRequestType());
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

    /**
     * 保存消息并更新群成员最后发言时间
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     * @param bot      the bot
     */
    @OnGroup
    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
    public void updateLastSpeakTime(GroupMsg groupMsg, MsgSender sender, Bot bot) {
        // 更新群员最后发言时间

        Member member = checkMember(sender, groupMsg.getAccountInfo(), groupMsg.getGroupInfo(), memberService);

        // 保存群员发送的消息
        MemberMessage memberMessage = new MemberMessage();
        memberMessage.setMsg(groupMsg.getMsg());
        memberMessage.setMemberId(member.getId());
        memberMessageService.save(memberMessage);
    }
}
