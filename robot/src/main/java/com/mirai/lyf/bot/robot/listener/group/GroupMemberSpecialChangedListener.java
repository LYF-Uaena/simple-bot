package com.mirai.lyf.bot.robot.listener.group;

import love.forte.simbot.api.message.events.GroupMemberSpecialChanged;
import love.forte.simbot.api.sender.MsgSender;

/**
 * 监听群友头衔变动事件
 */
//@Slf4j
//@Component
public class GroupMemberSpecialChangedListener {

//    @OnGroupMemberSpecialChanged
//    @Filters(customMostMatchType = MostMatchType.ALL, customFilter = {CustomerFilter.SPEAKING_ROBOT,
//            CustomerFilter.FORMAL_GROUP})
    public void memberSpecialChanged(GroupMemberSpecialChanged msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, "群头衔发生了改变");
    }
}
