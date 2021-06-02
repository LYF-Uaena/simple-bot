package com.mirai.lyf.bot.application.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * @author LYF
 * @create 2021-03-26 17:01
 * @desc
 **/
@Slf4j
@Component
public class Init implements CommandLineRunner {
    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public Init(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) throws NullPointerException {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            keys.forEach(redisTemplate::delete);
        }
    }
}
