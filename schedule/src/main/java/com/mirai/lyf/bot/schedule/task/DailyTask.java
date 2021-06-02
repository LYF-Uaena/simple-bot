package com.mirai.lyf.bot.schedule.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The type Task.
 *
 * @author LYF on 2021-01-03
 * @description
 */
@Service
@Slf4j
public class DailyTask implements Runnable {
    /**
     * The Bot manager.
     */
    private final BotManager botManager;

    @Autowired
    public DailyTask(BotManager botManager) {
        this.botManager = botManager;
    }


    /**
     * Good morning.
     */
    @Override
    public void run() {
        Bot bot = botManager.getBot("2622879283");
        MsgSender sender = bot.getSender();
        log.info("Good morning：" + new Date().toString());
        sender.SENDER.sendGroupMsg(TencentCode.GroupCode.BOT_GROUP, "清晨起来！拥抱太阳！满满正能量！");
    }
}