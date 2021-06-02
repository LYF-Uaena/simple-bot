package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LYF
 */
@Repository
public interface OperateLogRepository extends JpaRepository<OperateLog, Long> {
    /**
     * 根据群号和QQ号码查询对应的操作记录
     *
     * @param groupCode      群号
     * @param beOperatorCode QQ号
     * @return 操作历史记录 list
     */
    List<OperateLog> findAllByGroupCodeAndBeOperatorCode(Long groupCode, Long beOperatorCode);

    /**
     * 根据群号和QQ号码查询对应的操作记录
     *
     * @param groupCode      群号
     * @param beOperatorCode QQ号
     * @param reason         理由
     * @return 操作历史记录
     */
    List<OperateLog> findAllByGroupCodeAndBeOperatorCodeAndReason(Long groupCode, Long beOperatorCode, String reason);
}
