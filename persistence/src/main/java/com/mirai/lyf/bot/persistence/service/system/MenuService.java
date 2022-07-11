package com.mirai.lyf.bot.persistence.service.system;

import com.mirai.lyf.bot.persistence.domain.system.SysMenu;
import com.mirai.lyf.bot.persistence.repository.system.MenuRepository;
import com.mirai.lyf.bot.persistence.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单功能开关
 *
 * @author LYF.UAENA
 * @since 2022年03月27日 13:51
 */
@Service
@Slf4j
public class MenuService extends CommonService<SysMenu, MenuRepository> {
    private final RedisTemplate<String, Serializable> redisTemplate;

    /**
     * Instantiates a new Config service.
     *
     * @param repo the config repository
     */
    @Autowired
    public MenuService(MenuRepository repo, RedisTemplate<String, Serializable> redisTemplate) {
        super(repo);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询所有
     */
    public List<SysMenu> findAll() {
        return repo.findAll();
    }

//    /**
//     * 获取配置值
//     */
//    public String findValue(String code) {
//        Config config = find(code);
//        return Optional.ofNullable(config).map(Config::getValue).orElse("");
//    }
//
//    /**
//     * 获取配置
//     */
//    public Config find(String code) {
//        String prefixKey = PREFIX_KEY + code;
//        Config config = (Config) redisTemplate.opsForValue().get(prefixKey);
//        if (config != null) {
//            return config;
//        }
////        config = repo.findConfigByCode(code);
//        redisTemplate.opsForValue().set(prefixKey, config);
//        redisTemplate.expire(prefixKey, 3600L, TimeUnit.SECONDS);
//        return config;
//    }
//
//    public void setRedis(String key, Serializable value) {
//        key = PREFIX_KEY + key;
//        redisTemplate.opsForValue().set(key, value);
//        redisTemplate.expire(key, 3600L, TimeUnit.SECONDS);
//
//    }
//
//    public Serializable getRedis(String key) {
//        key = PREFIX_KEY + key;
//        return redisTemplate.opsForValue().get(key);
//    }
}
