//package com.mirai.lyf.bot.robot.filter;
//
//import com.mirai.lyf.bot.persistence.domain.system.Config;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * The type Base filter.
// *
// * @author LYF
// * @since 2022年07月14日 23:40
// */
//@Slf4j
//public class BaseFilter {
//
//    public static ConfigService configService;
//
//    @Autowired
//    public BaseFilter(ConfigService configService) {
//        BaseFilter.configService = configService;
//    }
//
//    static boolean compare(Config config, String code) {
//        return config.getValue().contains(code);
//    }
//}
