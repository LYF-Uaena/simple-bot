package com.mirai.lyf.bot.schedule;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author LYF
 * @create 2021-02-26 14:59
 * @desc
 **/
@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan
public class ScheduleModule {
    /**
     * The thread map.
     */
    public static ConcurrentHashMap<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>();
}
