//package com.mirai.lyf.bot.robot.filter;
//
//import catcode.Neko;
//import com.mirai.lyf.bot.common.kit.MessageType;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.api.message.events.MessageGet;
//import love.forte.simbot.api.message.events.MsgGet;
//import love.forte.simbot.filter.FilterData;
//import love.forte.simbot.filter.ListenerFilter;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//
///**
// * 图片消息过滤
// *
// * @author LYF on 2020-09-29
// */
//@Slf4j
//@Component("isImageMsg")
//public class ImageFilter implements ListenerFilter {
//
//    @Override
//    public boolean test(@NotNull FilterData data) {
//        MsgGet msgGet = data.getMsgGet();
//        AtomicBoolean isImage = new AtomicBoolean(Boolean.FALSE);
//        if (msgGet instanceof MessageGet) {
//            List<Neko> nekoList = ((MessageGet) msgGet).getMsgContent().getCats();
//            nekoList.forEach(neko -> {
//                if (MessageType.IMAGE.equals(neko.getType())) {
//                    isImage.set(Boolean.TRUE);
//                }
//            });
//        }
//        return isImage.get();
//    }
//}
