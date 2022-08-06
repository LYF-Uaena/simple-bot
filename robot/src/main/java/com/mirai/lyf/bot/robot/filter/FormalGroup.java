//package com.mirai.lyf.bot.robot.filter;
//
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import com.sun.media.jfxmedia.logging.Logger;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simboot.interceptor.AnnotatedEventListenerInterceptor;
//import love.forte.simbot.core.event.CoreInterceptUtil;
//import love.forte.simbot.core.event.CoreListenerManager;
//import love.forte.simbot.core.event.CoreListenerManagerConfiguration;
//import love.forte.simbot.core.event.SimpleListenerManagerConfiguration;
//import love.forte.simbot.event.EventListenerInterceptor;
//import love.forte.simbot.event.EventResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
///**
// * @author LYF
// * @create 2021-03-26 14:45
// * @desc 正式群
// **/
//@Slf4j
//@Component("formalGroup")
//public class FormalGroup /*extends BaseFilter*/ implements AnnotatedEventListenerInterceptor {
//
//    public EventResult intercept(Context context) {
//        System.out.println("拦截之前");
//        final EventResult result = context.proceedBlocking();
//        System.out.println("拦截之后");
//        return result;
//    }
//
////    @Override
////    public boolean test(@NotNull FilterData data) {
////        Config config = configService.find(ConfigCodeKit.FORMAL_GROUP);
////        MsgGet msgGet = data.getMsgGet();
////        String code = "";
////
////        // 获取当前消息群号码
////        if (msgGet instanceof GroupContainer) {
////            GroupContainer groupContainer = (GroupContainer) msgGet;
////            code = groupContainer.getGroupInfo().getGroupCode();
////        }
////        return compare(config, code);
////    }
//
//}
