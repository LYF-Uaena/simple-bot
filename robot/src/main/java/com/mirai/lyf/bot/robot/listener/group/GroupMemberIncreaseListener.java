package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.kit.Welcome;
import com.mirai.lyf.bot.common.utils.DateUtils;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.OperateLogService;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupMemberIncrease;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BeOperatorInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMemberIncrease;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听群友增加事件
 */
@Slf4j
@Component
public class GroupMemberIncreaseListener extends BaseListener {

    private final OperateLogService operateLogService;
    private final MemberService memberService;

    @Autowired
    public GroupMemberIncreaseListener(MessageContentBuilderFactory builderFactory, ConfigService configService, OperateLogService operateLogService, MemberService memberService) {
        super(builderFactory, configService);
        this.operateLogService = operateLogService;
        this.memberService = memberService;
    }

    /**
     * 群成员增加
     */
    @OnGroupMemberIncrease
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FORMAL_GROUP})
    public void groupMemberIncreaseListener(GroupMemberIncrease increaseMsg, MsgSender sender) {
        log.info("新增了一名群成员");
        GroupInfo groupInfo = increaseMsg.getGroupInfo();
        AccountInfo accountInfo = increaseMsg.getAccountInfo();
        BeOperatorInfo beOperatorInfo = increaseMsg.getBeOperatorInfo();

        long groupCode = groupInfo.getGroupCodeNumber();
        long beOperatorCode = beOperatorInfo.getBeOperatorCodeNumber();


        // 发送欢迎新成员的信息
        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
        builder.at(accountInfo.getAccountCode()).text(Welcome.getWelcome());
        sender.SENDER.sendGroupMsg(groupCode, builder.build());

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
        String msgPushGroup = configService.findValue(ConfigCodeKit.MSG_PUSH_GROUP);
        builder.clear();
        builder.text(groupInfo.getGroupName() + " 新增了【" + beOperatorInfo.getBeOperatorNicknameAndRemark() + "】，QQ号码为：" + beOperatorInfo.getBeOperatorCodeNumber() + "的成员。");

        sender.SENDER.sendGroupMsg(msgPushGroup, builder.build());
    }
}
