//package com.mirai.lyf.bot.robot.filter;
//
//import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
//import com.mirai.lyf.bot.persistence.domain.system.Config;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.api.message.events.MsgGet;
//import love.forte.simbot.filter.FilterData;
//import love.forte.simbot.filter.ListenerFilter;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author LYF
// * @create 2021-03-26 14:45
// * @desc 测试群
// **/
//@Slf4j
//@Component("testGroup")
//public class TestGroup extends BaseFilter implements ListenerFilter {
//
//    @Autowired
//    public TestGroup(ConfigService configService) {
//        super(configService);
//    }
//
//    @Override
//    public boolean test(@NotNull FilterData data) {
//        Config config = configService.find(ConfigCodeKit.TEST_GROUP);
//        MsgGet msgGet = data.getMsgGet();
//
////        if (msgGet instanceof MiraiGroupMsg) {
////            MiraiGroupMsg groupMsg = (MiraiGroupMsg) msgGet;
////            String code = groupMsg.getGroupInfo().getGroupCode();
////            log.info("group code = " + code);
////            return compare(config, code);
////        }
////        if (msgGet instanceof MiraiGroupMemberJoinRequest) {
////            MiraiGroupMemberJoinRequest joinRequest = (MiraiGroupMemberJoinRequest) msgGet;
////            String code = joinRequest.getGroupInfo().getGroupCode();
////            log.info("join code = " + code);
////            return compare(config, code);
////        }
//        return false;
//    }
//
//}
