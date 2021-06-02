package com.mirai.lyf.bot.robot.listener;

import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.assists.Permissions;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.message.results.GroupMemberList;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员功能
 *
 * @author LYF
 */
@Slf4j
@Component
public class FeaturesListener extends BaseListener {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final RosterService rosterService;
    private final MemberService memberService;
    private final BotManager botManager;


    @Autowired
    public FeaturesListener(MessageContentBuilderFactory builderFactory, RedisTemplate<String, Integer> redisTemplate,
                            RosterService rosterService, MemberService memberService, BotManager botManager) {
        super(builderFactory);
        this.redisTemplate = redisTemplate;
        this.rosterService = rosterService;
        this.memberService = memberService;
        this.botManager = botManager;
    }


    //    @OnGroup
//    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
    public void ss(GroupMsg groupMsg, MsgSender sender) {
        MessageContent messageContent = builderFactory.getMessageContentBuilder().text("Hi!").build();
        sendGroupMessage(groupMsg.getGroupInfo(), messageContent, sender);
    }

    /**
     * Roster listener.
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
     * Roster listener.
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


    /**
     * Limit listener.
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
        if (count > 5) {
            sender.SENDER.sendGroupMsg(groupMsg, "10s内已连续发送超过5条消息");
        }
    }


//    @Listen(MiraiBotOffline.class)
//    public void botOffline(Bot bot) {
//        log.info("======================================================================");
//        log.error(bot.getBotInfo().getAccountCode());
//        botManager.registerBot(InitBot.botVerifyInfoList.get(bot.getBotInfo().getAccountCode()));
//        log.info("======================================================================");
//    }

}
