package com.mirai.lyf.bot.schedule;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

/**
 * The type Dynamic task.
 *
 * @author LYF.UAENA
 * @since 2022年07月17日 10:43
 */
@Service
public class DynamicTask {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public DynamicTask(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public <T> void startCron(String cron, String name, T t) {
        System.out.println(Thread.currentThread().getName());
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule((Runnable) t, new CronTrigger(cron));
        ScheduleModule.map.put(name, future);
    }

    public void stop(String name) {
        ScheduledFuture<?> future = ScheduleModule.map.get(name);

        if (future != null) {
            future.cancel(true);
        }
    }

}
