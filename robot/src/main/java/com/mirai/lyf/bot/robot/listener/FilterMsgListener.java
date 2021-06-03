package com.mirai.lyf.bot.robot.listener;

import catcode.Neko;
import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.common.utils.PatternUtil;
import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.model.ImageResult;
import com.mirai.lyf.bot.persistence.model.master.ImageLogDto;
import com.mirai.lyf.bot.persistence.service.ImageService;
import com.mirai.lyf.bot.persistence.service.master.ImageLogDetailService;
import com.mirai.lyf.bot.persistence.service.master.ImageLogService;
import com.mirai.lyf.bot.persistence.service.master.MemberService;
import com.mirai.lyf.bot.persistence.service.master.RosterService;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The type Filter msg listener.
 *
 * @author LYF on 2021-03-03
 */
@Slf4j
@Component
public class FilterMsgListener extends BaseListener {
    private final ImageService imageService;
    private final ImageLogService imageLogService;
    private final ImageLogDetailService imageLogDetailService;
    private final ConfigService configService;
    private final RosterService rosterService;
    private final MemberService memberService;

    @Autowired
    public FilterMsgListener(MessageContentBuilderFactory builderFactory, ImageService imageService,
                             ImageLogService imageLogService, ImageLogDetailService imageLogDetailService,
                             ConfigService configService,
                             RosterService rosterService, MemberService memberService) {
        super(builderFactory);
        this.imageService = imageService;
        this.imageLogService = imageLogService;
        this.imageLogDetailService = imageLogDetailService;
        this.configService = configService;
        this.rosterService = rosterService;
        this.memberService = memberService;
    }

    /**
     * 图片审核
     *
     * @param groupMsg the group msg
     * @param sender   the sender
     */
    @OnGroup
    @Transactional
    @Filters(customFilter = {CustomerFilter.TEST_ROBOT, CustomerFilter.TEST_GROUP})
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
    public void imageMsgListener(GroupMsg groupMsg, MsgSender sender) {
        MessageContent images = groupMsg.getMsgContent();
        List<Neko> nekoList = images.getCats("image");
        nekoList.forEach(neko -> {
            String url = neko.get("url");

            // 调用pic api检测图片
            ImageResult imageResult = imageService.verifyPicture(url);
            log.info(imageResult.toString());
            // 查询当前成员
            Member member = checkMember(sender, groupMsg.getAccountInfo(), groupMsg.getGroupInfo(), memberService);

            // 从数据库查询对应图片检测结果
            ImageLog imageLog = imageLogService.findByImageIdAndMemberId(neko.get("id"), member.getId());
            if (imageLog == null) {
                imageLog = new ImageLog();
            }

            ImageLogDto imageLogDto = new ImageLogDto();

            imageLogService.buildImageLog(imageResult, neko, imageLogDto, member);

            BeanUtils.copyProperties(imageLogDto, imageLog);
            imageLogService.save(imageLog);

            List<ImageLogDetail> details = new ArrayList<>();
            long imageLogId = imageLog.getId();
            imageResult.getData().getReasonList().forEach(item -> {
                ImageLogDetail detail = new ImageLogDetail();
                detail.setImageLogId(imageLogId);
                detail.setMsg(item.getMsg());
                detail.setProbability(item.getProbability());
                detail.setLevel(item.getLevel());
                details.add(detail);
            });

            imageLogDetailService.saveAll(details);

            // 判断白名单成员
            Roster roster = rosterService.findByMemberCode(groupMsg.getAccountInfo().getAccountCodeNumber());
            if (roster != null && roster.getType() == Roster.Type.WHITE_ROSTER) {
                return;
            }

            // 图片审核结果处理
            if (imageLogDto.getCode() == HttpCode.SUCCESS && "不合规".equals(imageLog.getConclusion())) {
                // 构建需要发送的信息
                MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
                MessageContent messageContent = builder
                        .at(groupMsg.getAccountInfo().getAccountCode())
                        .text("消息违规了呢！先给你禁言一天的处罚吧，以后不要发了哦！发多了会被踢的呢！")
                        .build();
                // 撤回消息
                sender.SETTER.setMsgRecall(groupMsg.getFlag());
                // 禁言消息发送人
                sender.SETTER.setGroupBan(groupMsg.getGroupInfo(), groupMsg.getAccountInfo(), 1, TimeUnit.DAYS);
                // 发送消息
                sender.SENDER.sendGroupMsg(groupMsg, messageContent);
            }
        });
    }
}
