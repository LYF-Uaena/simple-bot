package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
import com.mirai.lyf.bot.persistence.domain.master.MemberMessage;
import com.mirai.lyf.bot.persistence.domain.system.SysMenu;
import com.mirai.lyf.bot.persistence.domain.system.SysMenuGroupInfo;
import com.mirai.lyf.bot.persistence.repository.system.MenuGroupInfoRepository;
import com.mirai.lyf.bot.persistence.service.master.MemberMessageService;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import com.mirai.lyf.bot.persistence.service.system.MenuService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Filters;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simboot.filter.MultiFilterMatchType;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Messages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 监听群聊消息
 */
@Slf4j
@Component
public class GroupListener extends BaseListener {
    @Autowired
    private MemberMessageService memberMessageService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RosterService rosterService;
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuGroupInfoRepository menuGroupInfoRepo;

    public GroupListener(ConfigService configService) {
        super(configService);
    }

    /**
     * 保存消息并更新群成员最后发言时间
     *
     * @param event the event
     */
    @Listener
    public void updateLastSpeakTime(GroupMessageEvent event) {
        // 更新群员最后发言时间
        MemberInfo memberInfo = checkMember(event, memberService);

        // 保存群员发送的消息
        MemberMessage memberMessage = new MemberMessage();
        Messages messages = event.getMessageContent().getMessages();
        String join = StringUtils.join(messages, ";");
        memberMessage.setMsg(join);
        memberMessage.setMemberId(memberInfo.getId());
        memberMessage.setMsgId(String.valueOf(event.getMessageContent().getMessageId()));
        memberMessageService.save(memberMessage);

        //        // 检测图片
        //        MessageContent images = groupMsg.getMsgContent();
        //        List<Neko> rich = images.getCats("rich");
        //        if (!CollectionUtils.isEmpty(rich)) {
        //            recallGroupMessage(groupMsg, sender);
        //            return;
        //        }
        //        List<Neko> nekoList = images.getCats("image");
        //        nekoList.forEach(neko -> {
        //            log.info("检测了一张来自{}的图片", groupMsg.getAccountInfo().getAccountCode());
        //            // 判断白名单成员
        //            Roster roster = rosterService.findByMemberCode(groupMsg.getAccountInfo().getAccountCodeNumber());
        //            if (roster != null && roster.getType() == Roster.Type.WHITE_ROSTER) {
        //                return;
        //            }
        //            imageMsg(groupMsg, sender, neko, memberInfo);
        //        });
    }
//
//    /**
//     * 图片审核
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
//    public void imageMsg(GroupMsg groupMsg, MsgSender sender, Neko neko, MemberInfo memberInfo) {
//
//        // 从数据库查询对应图片检测结果
//        ImageLog imageLog = imageLogService.findByImageIdAndMemberId(neko.get("id"), memberInfo.getId());
//
//        if (imageLog == null) {
//            imageLog = new ImageLog();
//        }
//
//        ImageLogDto imageLogDto = new ImageLogDto();
//        if (imageLog.getCode() != HttpCode.SUCCESS) {
//            // 调用pic api检测图片
//            imageLogDto = imageService.verifyPicture(neko, memberInfo, imageLog);
//        } else {
//            BeanUtils.copyProperties(imageLogDto, imageLog);
//        }
//
//        if (imageLogDto.getCode() != HttpCode.SUCCESS) {
//            // TODO 图片api返回结果失败
//            return;
//        }
//        // 消息构建器
//        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//        Carrier<Boolean> carrier = new Carrier<>(true);
//        // 图片审核结果处理
//        if (imageLogDto.getProbability() == null || (imageLogDto.getProbability() != null && imageLogDto.getProbability() < 0.8)) {
//            return;
//        }
//        // 违规处理
//        if (ImageLog.ConclusionType.NON_COMPLIANCE == imageLogDto.getConclusionType()) {
//            // 撤回消息，成功后发送禁言通知
//            carrier = sender.SETTER.setMsgRecall(groupMsg.getFlag());
//            if (carrier.get()) {
//                builder.clear()
//                        .at(groupMsg.getAccountInfo().getAccountCode())
//                        .text("消息违规了呢！先给你禁言一天的处罚吧，以后不要发了哦！发多了会被踢的呢！");
//                // 发送消息
//                sender.SENDER.sendGroupMsg(groupMsg, builder.build());
//                // 禁言消息发送人
//                sender.SETTER.setGroupBan(groupMsg.getGroupInfo(),
//                        groupMsg.getAccountInfo(), 1, TimeUnit.DAYS);
//            }
//        }
//
//        // 疑似处理
//        if (ImageLog.ConclusionType.SUSPECT == imageLogDto.getConclusionType()) {
//            // 撤回消息
//            carrier = sender.SETTER.setMsgRecall(groupMsg.getFlag());
//            if (carrier.get()) {
//                builder.clear()
//                        .at(groupMsg.getAccountInfo().getAccountCode())
//                        .text("消息似乎有点不对劲，先帮你撤回啦！！");
//                // 发送消息
//                sender.SENDER.sendGroupMsg(groupMsg, builder.build());
//            }
//        }
//
//        // 若权限不足，发送此消息
//        if (!carrier.get()) {
//            builder.clear()
//                    .at(groupMsg.getAccountInfo().getAccountCode())
//                    .text("以你的权限来看，我似乎做不了什么！建议寻求群主帮助降低权限呢！");
//            // 发送权限不足的消息
//            sender.SENDER.sendGroupMsg(groupMsg, builder.build());
//        }
//    }

//    /**
//     * 限制每分钟发言条数
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
////    @OnGroup
////    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
////            CustomerFilter.FORMAL_GROUP})
//    public void limitListener(GroupMsg groupMsg, MsgSender sender) {
//        String key = groupMsg.getGroupInfo().getGroupCode() + "-" + groupMsg.getAccountInfo().getAccountCode();
//        Integer count = redisTemplate.opsForValue().get(key);
//        redisTemplate.opsForValue().increment(key, 1);
//
//        if (count == null) {
//            redisTemplate.expire(key, Duration.ofSeconds(10));
//        }
//
//        count = redisTemplate.opsForValue().get(key);
//        if (count != null && count > 5) {
//            sender.SENDER.sendGroupMsg(groupMsg, "10s内已连续发送超过5条消息");
//        }
//    }

//    /**
//     * 更新白名单
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
////    @OnGroup
////    @Filter(value = "白名单", matchType = MatchType.CONTAINS, atBot = true, at = {"2635200012"}, codes = {"2635200012"})
//    public void rosterListener(GroupMsg groupMsg, MsgSender sender) {
//        // 获取群成员列表
//        GroupMemberList memberList = sender.GETTER.getGroupMemberList(groupMsg.getGroupInfo().getGroupCode());
//        List<Roster> rosters = new ArrayList<>();
//
//        for (GroupMemberInfo groupMemberInfo : memberList) {
//            // 添加所有管理员为白名单
//            if (Permissions.MEMBER != groupMemberInfo.getPermission()) {
//                if (rosterService.findByMemberCode(groupMemberInfo.getAccountCodeNumber()) == null) {
//                    Roster roster = new Roster();
//                    roster.setMemberCode(groupMemberInfo.getAccountCodeNumber());
//                    roster.setType(Roster.Type.WHITE_ROSTER);
//                    rosters.add(roster);
//                }
//            }
//        }
//
//        if (CollectionUtils.isEmpty(rosters)) {
//            return;
//        }
//        rosterService.saveAll(rosters);
//        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//        MessageContent msg = builder.text("成功添加白名单" + rosters.size() + "人！").build();
//
//        sender.SENDER.sendGroupMsg(groupMsg, msg);
//    }

//    /**
//     * 更新群成员信息
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
////    @OnGroup
////    @Filter(value = "更新群成员信息", matchType = MatchType.CONTAINS, at = {"2635200012"}, codes = {"571675921"})
//    public void memberListener(GroupMsg groupMsg, MsgSender sender) {
//        // 获取群成员列表
//        GroupMemberList memberList = sender.GETTER.getGroupMemberList(groupMsg.getGroupInfo().getGroupCode());
//        int count = 0;
//        for (GroupMemberInfo groupMemberInfo : memberList) {
//            MemberInfo memberInfo = buildMember(groupMemberInfo);
//
//            if (memberService.findByGroupCodeAndQqCode(memberInfo.getGroupCode(), memberInfo.getMemberCode()) == null) {
//                memberService.save(memberInfo);
//                count++;
//            }
//        }
//
//        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//        MessageContent msg = builder.text("更新成功，共更新" + count + "人！").build();
//
//        sender.SENDER.sendGroupMsg(groupMsg, msg);
//    }
//

//    /**
//     * 获取最后发言时间
//     *
//     * @param groupMsg the group msg
//     * @param sender   the sender
//     */
//    @Listener
//    @Filter(value = "#发言时间", matchType = MatchType.TEXT_STARTS_WITH, targets = @Filter.Targets(bots = {"2635200012"}, authors = {"571675921"}, atBot = true))
//    public void Listener(GroupMessageEvent messageEvent) {
//        if (groupMsg.getAccountInfo().getAccountCodeNumber() == 571675921L) {
//
//            // 获取群成员列表
//            GroupMemberList memberList = sender.GETTER.getGroupMemberList(groupMsg.getGroupInfo().getGroupCode());
//
//            MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//            int count = 0;
//            for (GroupMemberInfo info : memberList) {
//                Date date = new Date(info.getLastSpeakTime());
//                Calendar instance = Calendar.getInstance();
//                instance.setTime(date);
//
//                instance.set(Calendar.YEAR, 1);
//                Date time = instance.getTime();
//                if (date.before(time)) {
//                    count++;
//                }
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String sd = sdf.format(date);
//                builder.text(info.getAccountNickname() + "的最后发言时间为：" + sd + "\n");
//
//            }
//            sender.SENDER.sendGroupMsg(groupMsg, builder.build());
////            sender.SENDER.sendGroupMsg(groupMsg, "当前共有" + count + "人超过一年未发言");
//        } else {
//            sender.SENDER.sendGroupMsg(groupMsg, "您当前没有权限哦！");
//        }
//    }


    /**
     * 菜单
     *
     * @param messageEvent the message event
     */
    @Listener
    @Filter(value = "#菜单", matchType = MatchType.TEXT_STARTS_WITH)
    public void menu(GroupMessageEvent messageEvent) {
        List<SysMenu> all = menuService.findAll();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < all.size(); i++) {
            SysMenu sysMenu = all.get(i);
            msg.append(i + 1).append(": ").append(sysMenu.getName());
            if (i < all.size() - 1) {
                msg.append("\n");
            }
        }
        messageEvent.getGroup().sendBlocking(msg.toString());
    }

    /**
     * 功能开关
     */
    @Listener
    @Filters(
            multiMatchType = MultiFilterMatchType.ANY,
            value = {
                    @Filter(value = "#开启", matchType = MatchType.TEXT_CONTAINS, targets = @Filter.Targets(bots = {"2635200012"}, authors = {"571675921"}, atBot = true)),
                    @Filter(value = "#关闭", matchType = MatchType.TEXT_CONTAINS, targets = @Filter.Targets(bots = {"2635200012"}, authors = {"571675921"}, atBot = true))
            }
    )
    @Transactional
    public void Listener(GroupMessageEvent messageEvent) {
        String groupCode = messageEvent.getGroup().getId().toString();
        String plainText = messageEvent.getMessageContent().getPlainText();
        String[] s = plainText.split(" ");
        String code = s[s.length - 1];
        boolean a = plainText.startsWith("#开启");
        if (plainText.startsWith("#关闭")) {
            a = false;
        }
        SysMenuGroupInfo byCodeAndAndGroupCode = menuGroupInfoRepo.findByCodeAndAndGroupCode(code, groupCode);
        byCodeAndAndGroupCode.setStatus(a);
        menuGroupInfoRepo.save(byCodeAndAndGroupCode);
    }

}
