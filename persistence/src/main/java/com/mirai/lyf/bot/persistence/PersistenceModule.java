package com.mirai.lyf.bot.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author LYF
 * @create 2021-02-26 8:53
 * @desc
 **/
@Configuration
@ComponentScan
@EnableJpaAuditing
@EnableJpaRepositories({"com.mirai.lyf.bot.persistence.repository"})
@EntityScan({"com.mirai.lyf.bot.persistence.domain"})
public class PersistenceModule {
}
