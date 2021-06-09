package com.mirai.lyf.bot.robot.init;

import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.bot.BotVerifyInfo;
import love.forte.simbot.bot.BotVerifyInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 初始化bot
 *
 * @author LYF
 */
@Slf4j
@Component
public class InitBot implements ApplicationRunner {
    private final BotManager botManager;
    private final ConfigService configService;
    public static final HashMap<String, BotVerifyInfo> botVerifyInfoList = new HashMap<>();

    @Autowired
    public InitBot(BotManager botManager, ConfigService configService) {
        this.botManager = botManager;
        this.configService = configService;
    }

//    @Override
//    public void run(String... args) {
//        Config config = configService.find(ConfigCodeKit.TEST_BOTS);
//
//        String[] bots = config.getValue().split(",");
//
//        for (String bot : bots) {
//            final String[] split = bot.split(":");
//            BotVerifyInfo botVerifyInfo = BotVerifyInfos.getInstance(split[0], split[1]);
//            botVerifyInfoList.put(split[0], botVerifyInfo);
//            botManager.registerBot(botVerifyInfo);
//        }
//
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Config config = configService.find(ConfigCodeKit.TEST_BOTS);

        String[] bots = config.getValue().split(",");

        for (String bot : bots) {
            final String[] split = bot.split(":");
            BotVerifyInfo botVerifyInfo = BotVerifyInfos.getInstance(split[0], split[1]);
            botVerifyInfoList.put(split[0], botVerifyInfo);
            botManager.registerBot(botVerifyInfo);
        }
    }
}