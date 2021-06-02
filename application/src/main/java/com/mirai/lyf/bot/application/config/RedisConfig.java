package com.mirai.lyf.bot.application.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * The type Redis config.
 *
 * @author LYF on 2021-25-02
 * @description
 * @create 2021 -02-24 14:40
 * @desc
 */
@Configuration
@EnableCaching
@EnableRedisRepositories
@Slf4j
public class RedisConfig {
    /**
     * Redis Cache 设置
     *
     * @return Redis Cache 设置
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 实体延时
                .entryTtl(Duration.ofSeconds(600))
                // 键序列号
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                // 值序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                // 不缓存 null
                .disableCachingNullValues();
    }

    /**
     * cacheManager
     *
     * @param factory Redis 连接工厂
     * @return
     */
    @Bean()
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        log.info("初始化 Redis cacheManager");
        return RedisCacheManager.builder(factory).cacheDefaults(
                cacheConfiguration()).transactionAware().build();
    }

    /**
     * JSON Template
     *
     * @param factory Redis 连接工厂
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        log.info("初始化 Redis redisTemplate");
        // 配置redisTemplate
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // key序列化
        redisTemplate.setKeySerializer(keySerializer());
        // value序列化
        redisTemplate.setValueSerializer(valueSerializer());
        // Hash key序列化
        redisTemplate.setHashKeySerializer(keySerializer());
        // Hash value序列化
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    /**
     * 字符串 Template
     *
     * @param factory Redis 连接工厂
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

    /**
     * 异常处理
     */
    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(@NotNull RuntimeException e, @NotNull Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }

    /**
     * 字符串序列化
     */
    private StringRedisSerializer _keySerializer;

    private RedisSerializer<String> keySerializer() {
        if (_keySerializer == null) {
            _keySerializer = new StringRedisSerializer();
        }
        return _keySerializer;
    }

    /**
     * JSON 序列化
     */
    private RedisSerializer<Object> _valueSerializer;

    private RedisSerializer<Object> valueSerializer() {
        if (_valueSerializer == null) {
            _valueSerializer = new GenericJackson2JsonRedisSerializer();
        }
        return _valueSerializer;
    }
}
