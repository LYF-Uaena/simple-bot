package com.mirai.lyf.bot.robot;


import kotlin.Unit;
import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import love.forte.simbot.application.Applications;
import love.forte.simbot.component.mirai.MiraiComponent;
import love.forte.simbot.component.mirai.bot.MiraiBotManager;
import love.forte.simbot.core.application.Simple;
import love.forte.simbot.core.application.SimpleApplication;
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
    public RobotModule() {

        SimpleApplication application = Applications.buildSimbotApplication(Simple.INSTANCE)
                .build((builder, config) -> {
                    // 安装mirai组件
                    builder.install(MiraiComponent.Factory, (config1, perceivable) -> Unit.INSTANCE);
                    builder.install(MiraiBotManager.Factory, (config1, perceivable) -> Unit.INSTANCE);
                }).createBlocking();
    }
}
