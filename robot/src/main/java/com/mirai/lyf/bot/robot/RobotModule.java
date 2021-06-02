package com.mirai.lyf.bot.robot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The type Robot module.
 *
 * @author LYF on 2021-26-02
 * @description QQ机器人模块
 */
@Configuration
@EnableSimbot
@ComponentScan("com.mirai.lyf.bot.robot")
public class RobotModule {
}
