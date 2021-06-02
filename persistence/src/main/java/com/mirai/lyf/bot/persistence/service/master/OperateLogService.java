package com.mirai.lyf.bot.persistence.service.master;

import com.mirai.lyf.bot.persistence.domain.master.OperateLog;
import com.mirai.lyf.bot.persistence.repository.master.OperateLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYF
 */
@Service
public class OperateLogService {
    private final OperateLogRepository repo;

    @Autowired
    public OperateLogService(OperateLogRepository repo) {
        this.repo = repo;
    }

    /**
     * 根据群号和QQ号码查询对应的操作记录
     *
     * @param groupCode      群号
     * @param beOperatorCode QQ号
     * @return 操作历史记录
     */
    public List<OperateLog> findAll(Long groupCode, Long beOperatorCode) {
        return repo.findAllByGroupCodeAndBeOperatorCode(groupCode, beOperatorCode);
    }

    /**
     * 根据群号、QQ号码和理由查询对应的操作记录
     *
     * @param groupCode      群号
     * @param beOperatorCode QQ号
     * @param reason         理由
     * @return 操作历史记录
     */
    public List<OperateLog> findAll(Long groupCode, Long beOperatorCode, String reason) {
        return repo.findAllByGroupCodeAndBeOperatorCodeAndReason(groupCode, beOperatorCode, reason);
    }

    /**
     * 保存管理员操作历史记录
     *
     * @param operateLog 需要保存的记录信息
     */
    public void save(OperateLog operateLog) {
        repo.save(operateLog);
    }
}
