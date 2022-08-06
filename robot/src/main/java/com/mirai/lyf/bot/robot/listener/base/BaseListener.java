//package com.mirai.lyf.bot.robot.listener.base;
//
//import catcode.CatCodeUtil;
//import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
//import com.mirai.lyf.bot.common.utils.DateUtils;
//import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
//import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
//import com.mirai.lyf.bot.persistence.domain.system.Config;
//import com.mirai.lyf.bot.persistence.service.master.MemberService;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//
//import love.forte.simbot.Identifies;
//import love.forte.simbot.definition.GroupInfo;
//import love.forte.simbot.message.At;
//import love.forte.simbot.message.MessageContent;
//import love.forte.simbot.message.MessagesBuilder;
//import net.mamoe.mirai.internal.network.protocol.data.proto.GroupOpenSysMsg;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.security.Permissions;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
///**
// * The type Base listener.
// *
// * @author LYF
// */
//public class BaseListener {
//
//    public static final CatCodeUtil catCodeUtil = CatCodeUtil.INSTANCE;
//    public final ConfigService configService;
//
//    @Autowired
//    public BaseListener(ConfigService configService) {
//        this.configService = configService;
//    }
//
//
//    /**
//     * 发送群消息
//     *
//     * @param groupInfo the group info
//     * @param content   the content
//     * @param sender    the sender
//     */
//    public void sendGroupMessage(GroupInfo groupInfo, MessageContent content) {
//        sender.SENDER.sendGroupMsg(groupInfo, content);
//        final At at = new At(Identifies.ID(123));
//        MessagesBuilder builder = new MessagesBuilder();
//    }
//
//    public void sendGroupBan(String beOperateAccountCode, MsgSender sender) {
//        Config config = configService.find(ConfigCodeKit.FORMAL_GROUP);
//        String[] split = config.getValue().split(",");
//        for (String groupCode : split) {
//            sender.SETTER.setGroupBan(groupCode, beOperateAccountCode, 1, TimeUnit.DAYS);
//        }
//    }
//
//    /**
//     * 发送群消息
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
//    public void recallGroupMessage(GroupMsg groupMsg, MsgSender sender) {
//        // 撤回消息
//        Carrier<Boolean> carrier = sender.SETTER.setMsgRecall(groupMsg.getFlag());
//        if (carrier.get()) {
////            MessageContentBuilder text = builderFactory.getMessageContentBuilder()
////                    .at(groupMsg.getAccountInfo().getAccountCode())
////                    .text("消息似乎有点不对劲，先帮你撤回啦！！");
//            // 发送消息
//            sender.SENDER.sendGroupMsg(groupMsg, "消息似乎有点不对劲，先帮你撤回啦！！");
//        }
//    }
//
//    /**
//     * Check member member.
//     *
//     * @param sender        the sender
//     * @param accountInfo   the account info
//     * @param groupInfo     the group info
//     * @param memberService the member service
//     *
//     * @return the member
//     */
//    @NotNull
//    public synchronized MemberInfo checkMember(MsgSender sender, AccountInfo accountInfo, GroupInfo groupInfo,
//                                               MemberService memberService) {
//        // 从数据库查询
//        MemberInfo member = memberService.findByGroupCodeAndQqCode(groupInfo.getGroupCodeNumber(),
//                accountInfo.getAccountCodeNumber());
//        if (member == null) {
//            // 获取当前群员信息
//            GroupMemberInfo memberInfo = sender.GETTER.getMemberInfo(groupInfo.getGroupCode(),
//                    accountInfo.getAccountCode());
//            member = buildMember(memberInfo);
//        }
//
//        // 判断当前日期是否发言
//        boolean isSameDay;
//        // 当前时间
//        LocalDateTime currentTime = DateUtils.now();
//        isSameDay = DateUtils.isSameDay(member.getLastSpeakTime(), currentTime);
//        if (!isSameDay) {
//            member.setLastSpeakTime(currentTime);
//            memberService.save(member);
//        }
//        return member;
//    }
//
//    /**
//     * Build member member.
//     *
//     * @param memberInfo the member info
//     *
//     * @return the member
//     */
//    @NotNull
//    public MemberInfo buildMember(GroupMemberInfo memberInfo) {
//        MemberInfo member = new MemberInfo();
//        member.setGroupCode(memberInfo.getGroupInfo().getGroupCodeNumber());
//        member.setMemberCode(memberInfo.getAccountCodeNumber());
//        member.setHeadUrl(memberInfo.getAccountAvatar());
//        member.setIdentity(memberInfo.getPermission().toString());
//        member.setNickName(memberInfo.getAccountNickname());
//        member.setRemark(memberInfo.getAccountRemark());
//        return member;
//    }
//
//    /**
//     * 创建操作记录实体类
//     * <p>
//     *
//     * @param <T>  the type parameter
//     * @param type the type
//     * @param msg  the msg
//     *
//     * @return 数据实体 operate log
//     */
//    @NotNull
//    public <T extends GroupContainer & OperatorContainer & BeOperatorContainer> OperateLog buildOperateLog(String type, T msg) {
//        long groupCode = msg.getGroupInfo().getGroupCodeNumber();
//        long operatorCode = Optional
//                .ofNullable(msg.getOperatorInfo())
//                .map(OperatorInfo::getOperatorCodeNumber)
//                .orElse(0L);
//        long beOperatorCode = Optional
//                .ofNullable(msg.getBeOperatorInfo())
//                .map(BeOperatorInfo::getBeOperatorCodeNumber)
//                .orElse(0L);
//        OperateLog operateLog = new OperateLog();
//        // 群号
//        operateLog.setGroupCode(groupCode);
//        // 操作人Q号
//        operateLog.setOperatorCode(operatorCode);
//        // 被操作人Q号
//        operateLog.setBeOperatorCode(beOperatorCode);
//        // 操作原因
//        operateLog.setReason(type);
//        return operateLog;
//    }
//
//    public String getIdentity(Permissions permission) {
//        String identity = "未知";
//        switch (permission) {
//            case OWNER:
//                identity = "群主";
//                break;
//            case MEMBER:
//                identity = "群员";
//                break;
//            case ADMINISTRATOR:
//                identity = "管理员";
//                break;
//        }
//        return identity;
//    }
//}
