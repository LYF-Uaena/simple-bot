package com.mirai.lyf.bot.application;

import com.mirai.lyf.bot.common.CommonModule;
import com.mirai.lyf.bot.persistence.PersistenceModule;
import com.mirai.lyf.bot.robot.RobotModule;
import com.mirai.lyf.bot.schedule.ScheduleModule;
import com.mirai.lyf.bot.web.WebModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The type Application module.
 *
 * @author LYF on 2021-27-02
 */
@Configuration
@Import({CommonModule.class, RobotModule.class, PersistenceModule.class, ScheduleModule.class, WebModule.class})
public class ApplicationModule {
}
