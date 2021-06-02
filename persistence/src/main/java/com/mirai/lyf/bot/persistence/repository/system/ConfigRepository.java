package com.mirai.lyf.bot.persistence.repository.system;

import com.mirai.lyf.bot.persistence.domain.system.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Config repository.
 *
 * @author LYF on 2021-25-02
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    /**
     * Find config by key config.
     *
     * @param code the code
     * @return the config
     */
    Config findConfigByCode(String code);
}
