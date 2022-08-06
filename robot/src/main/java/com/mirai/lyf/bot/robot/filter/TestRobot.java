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
// * @create 2021-03-26 14:11
// * @desc 测试机器人
// **/
//@Slf4j
//@Component("testRobot")
//public class TestRobot extends BaseFilter implements ListenerFilter {
//
//    @Autowired
//    public TestRobot(ConfigService configService) {
//        super(configService);
//    }
//
//    @Override
//    public boolean test(@NotNull FilterData data) {
//        Config config = configService.find(ConfigCodeKit.TEST_ROBOT);
//        String accountCode = data.getMsgGet().getBotInfo().getAccountCode();
//        return compare(config, accountCode);
//    }
//}
