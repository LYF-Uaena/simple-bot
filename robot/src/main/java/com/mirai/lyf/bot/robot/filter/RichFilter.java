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
///**
// * rich消息过滤
// *
// * @author LYF on 2020-09-29
// */
//@Slf4j
//@Component("isRichMsg")
//public class RichFilter implements ListenerFilter {
//
//    @Override
//    public boolean test(@NotNull FilterData data) {
//        MsgGet msgGet = data.getMsgGet();
//        AtomicBoolean isRich = new AtomicBoolean(Boolean.FALSE);
//        if (msgGet instanceof MessageGet) {
//            List<Neko> nekoList = ((MessageGet) msgGet).getMsgContent().getCats();
//            nekoList.forEach(neko -> {
//                if (MessageType.RICH.equals(neko.getType())) {
//                    isRich.set(Boolean.TRUE);
//                }
//            });
//        }
//        return isRich.get();
//    }
//}
