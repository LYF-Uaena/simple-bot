package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.Welcome;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.mirai.event.MiraiMemberJoinEvent;
import love.forte.simbot.definition.Member;
import love.forte.simbot.event.MemberIncreaseEvent;
import love.forte.simbot.message.At;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听群友增加事件
 */
@Slf4j
@Component
public class GroupMemberIncreaseListener {

    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private MemberService memberService;

    /**
     * 群成员增加
     */
    @Listener
    public void groupMemberIncreaseListener(MemberIncreaseEvent memberEvent) {
        log.info("新增了一名群成员");
        Member member = memberEvent.getAfter();
        // 发送欢迎新成员的信息
        // 构建消息（链）
        Message message = Messages.toMessages(
                new At(member.getId()),
                Text.of(Welcome.getWelcome())
        );

        if (memberEvent instanceof MiraiMemberJoinEvent) {
            ((MiraiMemberJoinEvent) memberEvent).getGroup().sendBlocking(message);
        }

//        // 保存操作记录
//        OperateLog operateLog = buildOperateLog(String.valueOf(increaseMsg.getIncreaseType()), increaseMsg);
//        operateLogService.save(operateLog);
//
//        // 保存新成员信息
//        GroupMemberInfo memberInfo = sender.GETTER.getMemberInfo(groupCode, beOperatorCode);
//        MemberInfo member = memberService.findByGroupCodeAndQqCode(groupCode, beOperatorCode);
//        // 若不存在，新增，存在则不处理
//        if (member == null) {
//            member = buildMember(memberInfo);
//            member.setLastSpeakTime(DateUtils.now());
//            memberService.save(member);
//        }
//        String msgPushGroup = configService.findValue(ConfigCodeKit.MSG_PUSH_GROUP);
//        builder.clear();
//        builder.text(groupInfo.getGroupName() + " 新增了【" + beOperatorInfo.getBeOperatorNicknameAndRemark() + "】，QQ号码为：" + beOperatorInfo.getBeOperatorCodeNumber() + "的成员。");
//
//        sender.SENDER.sendGroupMsg(msgPushGroup, builder.build());
    }
}
