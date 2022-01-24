//package com.mirai.lyf.bot.robot.annotation;
//
//import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
//import com.mirai.lyf.bot.persistence.service.system.ConfigService;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Slf4j
//@Aspect
//@Component
//public class ApiTimesAspect {
//    private final ConfigService configService;
//
//    @Autowired
//    public ApiTimesAspect(ConfigService configService) {
//        this.configService = configService;
//    }
//
//
//    @Before("@annotation(apiTimes)")
//    public void before(JoinPoint point, ApiTimes apiTimes) {
//        boolean limit = apiTimes.limit();
//        if (limit) {
//            Boolean redis = (Boolean) configService.getRedis(ConfigCodeKit.CAN_REQUEST_API);
//            if (redis != null && Objects.equals(redis, Boolean.FALSE)) {
//                throw new RuntimeException("套餐次数超限");
//            }
//        }
//
//    }
//
//    @AfterReturning(value = "@annotation(apiTimes)", returning = "returnValue")
//    public void after(JoinPoint point, ApiTimes apiTimes, Serializable returnValue) {
//        boolean limit = apiTimes.limit();
//        if (limit) {
//            System.out.println("方法返回值为：" + returnValue);
//            if (!(Boolean) returnValue) {
//                configService.setRedis(ConfigCodeKit.CAN_REQUEST_API, Boolean.FALSE);
//            }
//        }
//
//    }
//}
//
