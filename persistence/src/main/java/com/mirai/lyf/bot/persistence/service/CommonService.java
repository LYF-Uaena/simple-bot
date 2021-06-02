package com.mirai.lyf.bot.persistence.service;


import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * The type Common service.
 *
 * @author LYF on 2021-03-10
 */
public class CommonService<Entity extends Serializable, Repository extends JpaRepository<Entity, Long>> {
    public static final String PREFIX_KEY = "config_key_";
    public final Repository repo;

    public CommonService(Repository repo) {
        this.repo = repo;
    }

    /**
     * 保存
     *
     * @param entity the entity
     * @return the entity
     */
    public Entity save(Entity entity) {
        return repo.save(entity);
    }

    /**
     * Save all list.
     *
     * @param entities the entities
     * @return the list
     */
    public List<Entity> saveAll(List<Entity> entities) {
        return repo.saveAll(entities);
    }

}
