package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import com.mirai.lyf.bot.persistence.service.master.OperateLogService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupMute;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMute;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听群友被禁言事件
 */
@Slf4j
@Component
public class GroupMuteListener extends BaseListener {
    private final OperateLogService operateLogService;

    @Autowired
    public GroupMuteListener(MessageContentBuilderFactory builderFactory, OperateLogService operateLogService) {
        super(builderFactory);
        this.operateLogService = operateLogService;
    }

    @OnGroupMute
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
            CustomerFilter.FORMAL_GROUP})
    public void groupMuteListener(GroupMute muteMsg, MsgSender sender) {
        log.info("mute accountCode = {}", muteMsg.getAccountInfo().getAccountCode());
        // 保存操作记录
        OperateLog operateLog = buildOperateLog(String.valueOf(muteMsg.getMuteActionType()), muteMsg);
        operateLogService.save(operateLog);
//        MessageContentBuilder image = builderFactory.getMessageContentBuilder().image("classpath:static/welcome.gif");
//        sendGroupMessage(muteMsg.getGroupInfo(), image.build(), sender);
    }


}
