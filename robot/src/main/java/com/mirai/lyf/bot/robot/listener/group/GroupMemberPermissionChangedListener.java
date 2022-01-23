package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroupMemberPermissionChanged;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.assists.Permissions;
import love.forte.simbot.api.message.events.GroupMemberPermissionChanged;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 监听群成员权限变动事件
 */
@Slf4j
@Component
public class GroupMemberPermissionChangedListener extends BaseListener {

    public GroupMemberPermissionChangedListener(MessageContentBuilderFactory builderFactory) {
        super(builderFactory);
    }

    @OnGroupMemberPermissionChanged
    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
            CustomerFilter.FORMAL_GROUP})
    public void permissionChanged(GroupMemberPermissionChanged msg, MsgSender sender) {

        String s = "【" +
                msg.getBeOperatorInfo().getAccountNickname() +
                "】的身份由【" +
                getIdentity(Optional.ofNullable(msg.getBeforeChange()).orElse(Permissions.MEMBER)) +
                "】变更至【" +
                getIdentity(Optional.ofNullable(msg.getAfterChange()).orElse(Permissions.MEMBER)) +
                "】";
        MessageContentBuilder contentBuilder = builderFactory.getMessageContentBuilder()
                .at(msg.getBeOperatorInfo().getAccountCode())
                .text(s);

        sender.SENDER.sendGroupMsg(msg, contentBuilder.build());
    }
}
