package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.OnGroupMemberPermissionChanged;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMemberPermissionChanged;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.stereotype.Component;

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
    public void permissionChanged(GroupMemberPermissionChanged msg, MsgSender sender) {
        MessageContentBuilder contentBuilder = builderFactory.getMessageContentBuilder()
//                .at(msg.getBeOperatorInfo().getAccountCode())
                .text("群员【" + msg.getBeOperatorInfo().getAccountNickname() + "】的权限由【" + msg.getBeforeChange() + "】变更至【" + msg.getAfterChange() + "】");

        sender.SENDER.sendGroupMsg(msg, contentBuilder.build());
    }
}
