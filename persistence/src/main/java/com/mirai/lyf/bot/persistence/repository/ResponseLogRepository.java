package com.mirai.lyf.bot.persistence.repository;

import com.mirai.lyf.bot.persistence.domain.alapi.ResponseLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 接口返回log
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 22:06
 */
@Repository
public interface ResponseLogRepository extends JpaRepository<ResponseLogEntity, Long> {

}
