package com.mirai.lyf.bot.robot;


import kotlin.Unit;
import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import love.forte.simbot.application.Applications;
import love.forte.simbot.application.BotManagers;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.component.mirai.MiraiComponent;
import love.forte.simbot.component.mirai.bot.MiraiBot;
import love.forte.simbot.component.mirai.bot.MiraiBotManager;
import love.forte.simbot.core.application.Simple;
import love.forte.simbot.core.application.SimpleApplication;
import love.forte.simbot.core.event.SimpleListeners;
import love.forte.simbot.event.EventListener;
import love.forte.simbot.event.GroupMessageEvent;
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


        // 注册监听函数，首先构建所需的监听函数实例
        // 监听一个通用的群消息事件
        EventListener listener = SimpleListeners.listener(GroupMessageEvent.Key,
                // 事件匹配要求: 消息的文本为 "你好"
                (context, event) -> event.getMessageContent().getPlainText().trim().equals("你好"),
                // 如果匹配成功，回复 "你也好"
                (context, event) -> {
                    event.replyBlocking("你也好");
                });
        // 注册这个监听函数
        application.getEventListenerManager().register(listener);

        // 注册mirai的bot
        // 寻找并获取MiraiBotManager
        BotManagers botManagers = application.getBotManagers();
        for (BotManager<?> botManager : botManagers) {
            if (botManager instanceof MiraiBotManager miraiBotManager) {
                MiraiBot bot = miraiBotManager.register(111, "111", configuration -> {
                    // 组件配置
                    configuration.botConfiguration(originalMiraiConfiguration -> {
                        // mirai原生配置
                    });
                });

                bot.startBlocking();
                break; // 通常情况下只需要获取第一个，因此当匹配后终止寻找
            }
        }
    }
}
