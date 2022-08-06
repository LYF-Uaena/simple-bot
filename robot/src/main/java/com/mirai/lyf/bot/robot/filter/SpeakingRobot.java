//package com.mirai.lyf.bot.robot.filter;
//
//import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
//import com.mirai.lyf.bot.persistence.domain.system.Config;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import lombok.extern.slf4j.Slf4j;
//import love.forte.simbot.filter.FilterData;
//import love.forte.simbot.filter.ListenerFilter;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author LYF
// * @create 2021-03-26 14:45
// * @desc 普通发言机器人
// **/
//@Slf4j
//@Component("speakingRobot")
//public class SpeakingRobot extends BaseFilter implements ListenerFilter {
//
//    @Autowired
//    public SpeakingRobot(ConfigService configService) {
//        super(configService);
//    }
//
//    @Override
//    public boolean test(@NotNull FilterData data) {
//        Config config = configService.find(ConfigCodeKit.SPEAKING_ROBOT);
//        String accountCode = data.getMsgGet().getBotInfo().getAccountCode();
//        return compare(config, accountCode);
//    }
//
//}
