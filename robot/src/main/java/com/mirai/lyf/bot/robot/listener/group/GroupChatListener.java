//package com.mirai.lyf.bot.robot.listener.group;
//
//import com.mirai.lyf.bot.robot.listener.base.BaseListener;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.annotation.Filter;
//import love.forte.simbot.annotation.OnGroup;
//import love.forte.simbot.api.message.MessageContentBuilderFactory;
//import love.forte.simbot.api.message.events.GroupMsg;
//import love.forte.simbot.api.sender.MsgSender;
//import love.forte.simbot.filter.MatchType;
//import org.springframework.stereotype.Component;
//
///**
// * 群对话
// */
//@Slf4j
//@Component
//public class GroupChatListener extends BaseListener {
//
//    public GroupChatListener(MessageContentBuilderFactory builderFactory) {
//        super(builderFactory);
//    }
//
//
////    /**
////     * 对话
////     *
////     * @param groupMsg the group msg
////     * @param sender   the sender
////     */
////    @OnGroup
////    @Filter(value = "发言时间", matchType = MatchType.CONTAINS, bots = {"2635200012"}, atBot = true, at = {"2635200012"})
////    public void Listener(GroupMsg groupMsg, MsgSender sender) {
////
////    }
//}
