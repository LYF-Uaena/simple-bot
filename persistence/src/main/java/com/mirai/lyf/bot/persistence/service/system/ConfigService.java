package com.mirai.lyf.bot.persistence.service.system;

import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.repository.system.ConfigRepository;
import com.mirai.lyf.bot.persistence.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * The type Config service.
 *
 * @author LYF on 2021-25-02
 * @description
 */
@Service
@Slf4j
public class ConfigService extends CommonService<Config, ConfigRepository> {
    private final RedisTemplate<String, Serializable> redisTemplate;

    /**
     * Instantiates a new Config service.
     *
     * @param repo          the config repository
     * @param redisTemplate
     */
    @Autowired
    public ConfigService(ConfigRepository repo, RedisTemplate<String, Serializable> redisTemplate) {
        super(repo);
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public Config save(Config config) {
        return repo.save(config);
    }

    /**
     * 获取配置值
     */
    public String findValue(String code) {
        Config config = find(code);
        return Optional.ofNullable(config).map(Config::getValue).orElse("");
    }

    /**
     * 获取配置
     */
    public Config find(String code) {
        String prefixKey = PREFIX_KEY + code;
        Config config = (Config) redisTemplate.opsForValue().get(prefixKey);
        if (config != null) {
            return config;
        }
        config = repo.findConfigByCode(code);
        redisTemplate.opsForValue().set(prefixKey, config);
        redisTemplate.expire(prefixKey, 3600L, TimeUnit.SECONDS);
        return config;
    }

    public void setRedis(String key, Serializable value) {
        key = PREFIX_KEY + key;
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 3600L, TimeUnit.SECONDS);

    }

    public Serializable getRedis(String key) {
        key = PREFIX_KEY + key;
        return redisTemplate.opsForValue().get(key);
    }
}
