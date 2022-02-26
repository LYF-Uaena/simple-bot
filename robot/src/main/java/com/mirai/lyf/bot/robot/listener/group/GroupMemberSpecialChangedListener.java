package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupMemberSpecialChanged;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMemberSpecialChanged;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MostMatchType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 监听群友头衔变动事件
 */
@Slf4j
@Component
public class GroupMemberSpecialChangedListener extends BaseListener {

    public GroupMemberSpecialChangedListener(MessageContentBuilderFactory builderFactory, ConfigService configService) {
        super(builderFactory, configService);
    }

    @OnGroupMemberSpecialChanged
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FORMAL_GROUP})
    public void memberSpecialChanged(GroupMemberSpecialChanged msg, MsgSender sender) {
        String afterChange = msg.getAfterChange();
        String s;
        if (StringUtils.isEmpty(afterChange)) {
            s = "啊哦，您的头衔被收回了。";
        } else {
            s = "恭喜您获得头衔【" + afterChange + "】。";

        }
        MessageContentBuilder contentBuilder = builderFactory.getMessageContentBuilder().at(msg.getBeOperatorInfo().getAccountCode()).text(s);

        sender.SENDER.sendGroupMsg(msg, contentBuilder.build());
    }
}
