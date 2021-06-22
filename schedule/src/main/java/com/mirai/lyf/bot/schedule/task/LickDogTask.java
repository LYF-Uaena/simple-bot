package com.mirai.lyf.bot.schedule.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务：舔狗日记
 *
 * @author LYF on 2021-06-22
 */
@Slf4j
@Service
public class LickDogTask implements Runnable {
    /**
     * The Bot manager.
     */
    private final BotManager botManager;

    @Autowired
    public LickDogTask(BotManager botManager) {
        this.botManager = botManager;
    }


    /**
     * 请求并发送舔狗日记
     */
    @Override
    public void run() {

    }
}