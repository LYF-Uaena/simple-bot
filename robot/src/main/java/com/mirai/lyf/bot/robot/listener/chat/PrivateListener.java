//package com.mirai.lyf.bot.robot.listener.chat;
//
//import com.mirai.lyf.bot.common.kit.CustomerFilter;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import com.mirai.lyf.bot.robot.listener.base.BaseListener;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.annotation.Filter;
//import love.forte.simbot.annotation.Filters;
//import love.forte.simbot.annotation.OnPrivate;
//import love.forte.simbot.api.message.MessageContentBuilderFactory;
//import love.forte.simbot.api.message.Reply;
//import love.forte.simbot.api.message.ReplyAble;
//import love.forte.simbot.filter.MatchType;
//import love.forte.simbot.filter.MostMatchType;
//import org.springframework.stereotype.Component;
//
///**
// * 监听私聊消息
// */
//@Slf4j
//@Component
//public class PrivateListener extends BaseListener {
//
//    public PrivateListener(MessageContentBuilderFactory builderFactory, ConfigService configService) {
//        super(builderFactory, configService);
//    }
//
//    /**
//     * 网易云自动脚本
//     */
//    @OnPrivate
//    @Filters(
//            customMostMatchType = MostMatchType.ALL,
//            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FUNCTION},
//            mostMatchType = MostMatchType.ANY,
//            value = {
//                    @Filter(value = "网易云300", matchType = MatchType.STARTS_WITH)
//            }
//    )
//    public ReplyAble neteaseCloud() {
//        return Reply.reply("网易云功能暂未开发", Boolean.TRUE);
//    }
//}
