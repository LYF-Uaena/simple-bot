package com.mirai.lyf.bot.robot.listener.group;

import catcode.Neko;
import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.MemberMessage;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.model.alapi.LickDogData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.model.master.ImageLogDto;
import com.mirai.lyf.bot.persistence.service.alapi.ImageService;
import com.mirai.lyf.bot.persistence.service.alapi.LickDogService;
import com.mirai.lyf.bot.persistence.service.master.ImageLogService;
import com.mirai.lyf.bot.persistence.service.master.MemberMessageService;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.common.utils.Carrier;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.assists.Permissions;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.message.results.GroupMemberList;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 监听群聊消息
 */
@Slf4j
@Component
public class GroupListener extends BaseListener {
    private final MemberMessageService memberMessageService;
    private final MemberService memberService;
    private final ImageService imageService;
    private final ImageLogService imageLogService;
    private final RosterService rosterService;
    private final RedisTemplate<String, Integer> redisTemplate;
    private final LickDogService lickDogService;

    @Autowired
    public GroupListener(MessageContentBuilderFactory builderFactory, MemberMessageService memberMessageService,
                         MemberService memberService, ImageService imageService, ImageLogService imageLogService,
                         RosterService rosterService, RedisTemplate<String, Integer> redisTemplate,
                         LickDogService lickDogService) {
        super(builderFactory);
        this.memberMessageService = memberMessageService;
        this.memberService = memberService;
        this.imageService = imageService;
        this.imageLogService = imageLogService;
        this.rosterService = rosterService;
        this.redisTemplate = redisTemplate;
        this.lickDogService = lickDogService;
    }

    /**
     * 保存消息并更新群成员最后发言时间
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     * @param bot      the bot
     */
    @OnGroup
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
            CustomerFilter.FORMAL_GROUP})
    public void updateLastSpeakTime(GroupMsg groupMsg, MsgSender sender, Bot bot) {
        // 更新群员最后发言时间

        Member member = checkMember(sender, groupMsg.getAccountInfo(), groupMsg.getGroupInfo(), memberService);

        // 保存群员发送的消息
        MemberMessage memberMessage = new MemberMessage();
        memberMessage.setMsg(groupMsg.getMsg());
        memberMessage.setMemberId(member.getId());
        memberMessage.setMsgId(groupMsg.getId());
        memberMessageService.save(memberMessage);

        // 检测图片
        MessageContent images = groupMsg.getMsgContent();
        List<Neko> nekoList = images.getCats("image");
        nekoList.forEach(neko -> {
            log.info("检测了一张来自{}的图片", groupMsg.getAccountInfo().getAccountCode());
            // 判断白名单成员
            Roster roster = rosterService.findByMemberCode(groupMsg.getAccountInfo().getAccountCodeNumber());
            if (roster != null && roster.getType() == Roster.Type.WHITE_ROSTER) {
                return;
            }
            imageMsg(groupMsg, sender, neko, member);
        });
    }

    /**
     * 图片审核
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     */
    public void imageMsg(GroupMsg groupMsg, MsgSender sender, Neko neko, Member member) {

        // 从数据库查询对应图片检测结果
        ImageLog imageLog = imageLogService.findByImageIdAndMemberId(neko.get("id"), member.getId());

        if (imageLog == null) {
            imageLog = new ImageLog();
        }

        ImageLogDto imageLogDto = new ImageLogDto();
        if (imageLog.getCode() != HttpCode.SUCCESS) {
            // 调用pic api检测图片
            imageLogDto = imageService.verifyPicture(neko, member, imageLog);
        } else {
            BeanUtils.copyProperties(imageLogDto, imageLog);
        }

        if (imageLogDto.getCode() != HttpCode.SUCCESS) {
            // TODO 图片api返回结果失败
            return;
        }
        // 消息构建器
        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
        Carrier<Boolean> carrier = new Carrier<>(true);
        // 图片审核结果处理
        // 违规处理
        if (ImageLog.ConclusionType.NON_COMPLIANCE == imageLogDto.getConclusionType()) {
            // 撤回消息，成功后发送禁言通知
            carrier = sender.SETTER.setMsgRecall(groupMsg.getFlag());
            if (carrier.get()) {
                builder.clear()
                        .at(groupMsg.getAccountInfo().getAccountCode())
                        .text("消息违规了呢！先给你禁言一天的处罚吧，以后不要发了哦！发多了会被踢的呢！");
                // 发送消息
                sender.SENDER.sendGroupMsg(groupMsg, builder.build());
                // 禁言消息发送人
                sender.SETTER.setGroupBan(groupMsg.getGroupInfo(),
                        groupMsg.getAccountInfo(), 1, TimeUnit.DAYS);
            }

        }

        // 疑似处理
        if (ImageLog.ConclusionType.SUSPECT == imageLogDto.getConclusionType()) {
            // 撤回消息
            carrier = sender.SETTER.setMsgRecall(groupMsg.getFlag());
            if (carrier.get()) {
                builder.clear()
                        .at(groupMsg.getAccountInfo().getAccountCode())
                        .text("消息似乎有点不对劲，先帮你撤回啦！！");
                // 发送消息
                sender.SENDER.sendGroupMsg(groupMsg, builder.build());
            }
        }

        // 若权限不足，发送此消息
        if (!carrier.get()) {
            builder.clear()
                    .at(groupMsg.getAccountInfo().getAccountCode())
                    .text("以你的权限来看，我似乎做不了什么！建议寻求群主帮助降低权限呢！");
            // 发送权限不足的消息
            sender.SENDER.sendGroupMsg(groupMsg, builder.build());
        }
    }

    /**
     * 限制每分钟发言条数
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     */
//    @OnGroup
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
    public void limitListener(GroupMsg groupMsg, MsgSender sender) {
        String key = groupMsg.getGroupInfo().getGroupCode() + "-" + groupMsg.getAccountInfo().getAccountCode();
        Integer count = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().increment(key, 1);

        if (count == null) {
            redisTemplate.expire(key, Duration.ofSeconds(10));
        }

        count = redisTemplate.opsForValue().get(key);
        if (count != null && count > 5) {
            sender.SENDER.sendGroupMsg(groupMsg, "10s内已连续发送超过5条消息");
        }
    }

    /**
     * 更新白名单
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     */
//    @OnGroup
//    @Filter(value = "白名单", matchType = MatchType.CONTAINS, atBot = true, at = {"2635200012"}, codes = {"2635200012"})
    public void rosterListener(GroupMsg groupMsg, MsgSender sender) {
        // 获取群成员列表
        GroupMemberList memberList = sender.GETTER.getGroupMemberList(groupMsg.getGroupInfo().getGroupCode());
        List<Roster> rosters = new ArrayList<>();

        for (GroupMemberInfo groupMemberInfo : memberList) {
            // 添加所有管理员为白名单
            if (Permissions.MEMBER != groupMemberInfo.getPermission()) {
                if (rosterService.findByMemberCode(groupMemberInfo.getAccountCodeNumber()) == null) {
                    Roster roster = new Roster();
                    roster.setMemberCode(groupMemberInfo.getAccountCodeNumber());
                    roster.setType(Roster.Type.WHITE_ROSTER);
                    rosters.add(roster);
                }
            }
        }

        if (CollectionUtils.isEmpty(rosters)) {
            return;
        }
        rosterService.saveAll(rosters);
        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
        MessageContent msg = builder.text("成功添加白名单" + rosters.size() + "人！").build();

        sender.SENDER.sendGroupMsg(groupMsg, msg);
    }

    /**
     * 更新群成员信息
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     */
//    @OnGroup
//    @Filter(value = "更新群成员信息", matchType = MatchType.CONTAINS, at = {"2635200012"}, codes = {"571675921"})
    public void memberListener(GroupMsg groupMsg, MsgSender sender) {
        // 获取群成员列表
        GroupMemberList memberList = sender.GETTER.getGroupMemberList(groupMsg.getGroupInfo().getGroupCode());
        int count = 0;
        for (GroupMemberInfo groupMemberInfo : memberList) {
            Member member = buildMember(groupMemberInfo);

            if (memberService.findByGroupCodeAndQqCode(member.getGroupCode(), member.getMemberCode()) == null) {
                memberService.save(member);
                count++;
            }
        }

        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
        MessageContent msg = builder.text("更新成功，共更新" + count + "人！").build();

        sender.SENDER.sendGroupMsg(groupMsg, msg);
    }


    @OnGroup
    @Filters(
            customMostMatchType = MostMatchType.ALL,
            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FORMAL_GROUP},
            mostMatchType = MostMatchType.ANY,
            value = {
                    @Filter(value = "宝", matchType = MatchType.STARTS_WITH)
            }
    )
    public void lickDogListener(GroupMsg msg, MsgSender sender) {
        Response<LickDogData> lickDogResponse = lickDogService.lickDog();
        if (lickDogResponse.getCode() == HttpCode.SUCCESS) {
            MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
            builder.at(msg.getAccountInfo().getAccountCode())
                    .text(lickDogResponse.getData().getContent());

            sender.SENDER.sendGroupMsg(msg, builder.build());
        }
    }

}
