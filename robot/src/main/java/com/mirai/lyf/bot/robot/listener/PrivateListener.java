package com.mirai.lyf.bot.robot.listener;

import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.PrivateMsg;
import org.springframework.stereotype.Component;

/**
 * @author LYF
 * @create 2021-06-02 15:46
 * @desc 私聊监听
 **/
@Component
@Slf4j
public class PrivateListener extends BaseListener {

    public PrivateListener(MessageContentBuilderFactory builderFactory) {
        super(builderFactory);
    }

    @OnPrivate
    public void ss(PrivateMsg msg) {
        log.info(msg.getMsg());
    }
}
