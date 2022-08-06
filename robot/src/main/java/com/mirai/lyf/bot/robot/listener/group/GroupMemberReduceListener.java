//package com.mirai.lyf.bot.robot.listener.group;
//
//import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
//import com.mirai.lyf.bot.common.kit.CustomerFilter;
//import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
//import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
//import com.mirai.lyf.bot.persistence.domain.master.Roster;
//import com.mirai.lyf.bot.persistence.service.master.MemberService;
//import com.mirai.lyf.bot.persistence.service.master.OperateLogService;
//import com.mirai.lyf.bot.persistence.service.master.RosterService;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import com.mirai.lyf.bot.robot.listener.base.BaseListener;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.annotation.Filters;
//import love.forte.simbot.annotation.OnGroupMemberReduce;
//import love.forte.simbot.api.message.MessageContentBuilder;
//import love.forte.simbot.api.message.MessageContentBuilderFactory;
//import love.forte.simbot.api.message.containers.AccountInfo;
//import love.forte.simbot.api.message.containers.BeOperatorInfo;
//import love.forte.simbot.api.message.containers.GroupInfo;
//import love.forte.simbot.api.message.events.GroupMemberReduce;
//import love.forte.simbot.api.sender.MsgSender;
//import love.forte.simbot.filter.MostMatchType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * 监听群友减少事件
// */
//@Slf4j
//@Component
//public class GroupMemberReduceListener extends BaseListener {
//
//    private final OperateLogService operateLogService;
//    private final MemberService memberService;
//    private final RosterService rosterService;
//
//    @Autowired
//    public GroupMemberReduceListener(MessageContentBuilderFactory builderFactory, OperateLogService operateLogService
//            , MemberService memberService, RosterService rosterService, ConfigService configService) {
//        super(builderFactory, configService);
//        this.operateLogService = operateLogService;
//        this.memberService = memberService;
//        this.rosterService = rosterService;
//    }
//
//    /**
//     * 群成员减少
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @OnGroupMemberReduce
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
//    public void groupMemberReduceListener(GroupMemberReduce reduceMsg, MsgSender sender) {
//        log.info("群成员减一");
//        // 保存群成员变更记录
//        OperateLog operateLog = buildOperateLog(String.valueOf(reduceMsg.getReduceType()), reduceMsg);
//        operateLogService.save(operateLog);
//
//        GroupInfo groupInfo = reduceMsg.getGroupInfo();
//        BeOperatorInfo beOperatorInfo = reduceMsg.getBeOperatorInfo();
//        AccountInfo accountInfo = reduceMsg.getAccountInfo();
//
//        // 更新群员信息
//        MemberInfo memberInfo = memberService.findByGroupCodeAndQqCode(groupInfo.getGroupCodeNumber(),
//                beOperatorInfo.getBeOperatorCodeNumber());
//        boolean b = reduceMsg.getReduceType() == GroupMemberReduce.Type.KICK;
//        if (memberInfo != null) {
//            memberInfo.setStatus(b ? MemberInfo.Status.KICK :
//                    MemberInfo.Status.LEAVE);
//            memberService.save(memberInfo);
//        }
//        // 若成员是被踢出，加黑名单
//        if (b) {
//            Roster roster = new Roster();
//            roster.setMemberCode(accountInfo.getAccountCodeNumber());
//            roster.setType(Roster.Type.BLACK_ROSTER);
//            rosterService.save(roster);
//        }
//        String msgPushGroup = configService.findValue(ConfigCodeKit.MSG_PUSH_GROUP);
//        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//        builder.text(groupInfo.getGroupName() + " 的【" + beOperatorInfo.getBeOperatorNicknameAndRemark()
//                + "】，QQ号码为：" + beOperatorInfo.getBeOperatorCodeNumber() + "，" + (b ? "被踢出" : "主动退出") + "了群聊。");
//        sender.SENDER.sendGroupMsg(msgPushGroup, builder.build());
//
//    }
//}
