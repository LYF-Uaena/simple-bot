package com.mirai.lyf.bot.schedule;

import com.alibaba.fastjson.JSONObject;
import com.mirai.lyf.bot.schedule.task.DailyTask;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @create 2021-02-26 13:19
 * @desc
 **/
@Slf4j
@RestController
public class ScheduleTestController {
    private final DynamicTask dynamicTask;
    /**
     * The Bot manager.
     */
    private final BotManager botManager;

    @Autowired
    public ScheduleTestController(DynamicTask dynamicTask, BotManager botManager) {
        this.dynamicTask = dynamicTask;
        this.botManager = botManager;
    }

    /**
     * 更新 定时任务
     */
    @PostMapping("/sc")
    public void schedule(@RequestBody String params) {
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        String cron = paramsJSONObject.getString("cron");
        String name = paramsJSONObject.getString("name");
        DailyTask task = new DailyTask(botManager);
        dynamicTask.startCron(cron, name, task);
    }

    /**
     * 更新 定时任务
     */
    @GetMapping("/stop/{name}")
    public void stopSchedule(@PathVariable String name) {
        dynamicTask.stop(name);
    }
}
