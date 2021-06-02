package com.mirai.lyf.bot.persistence.service.master;

import com.mirai.lyf.bot.persistence.domain.master.Roster;
import com.mirai.lyf.bot.persistence.repository.master.RosterRepository;
import com.mirai.lyf.bot.persistence.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 黑、白名单
 *
 * @author LYF on 2021-03-10
 */
@Service
public class RosterService extends CommonService<Roster, RosterRepository> {
    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public RosterService(RosterRepository repo, RedisTemplate<String, Serializable> redisTemplate) {
        super(repo);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过QQ号码查询类型
     *
     * @param code the code
     * @return the roster
     */
    public Roster findByMemberCode(long code) {
        String prefixKey = PREFIX_KEY + code;
        Roster roster = (Roster) redisTemplate.opsForValue().get(prefixKey);
        if (roster != null) {
            return roster;
        }
        roster = repo.findByMemberCode(code);
        redisTemplate.opsForValue().set(prefixKey, roster);
        redisTemplate.expire(prefixKey, 3600L, TimeUnit.SECONDS);
        return roster;
    }

}
