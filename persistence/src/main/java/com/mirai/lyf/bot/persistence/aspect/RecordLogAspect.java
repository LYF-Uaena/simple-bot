package com.mirai.lyf.bot.persistence.aspect;

import com.mirai.lyf.bot.persistence.annation.RecordLog;
import com.mirai.lyf.bot.persistence.domain.alapi.ResponseLogEntity;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.repository.ResponseLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RecordLogAspect {
    @Autowired
    private ResponseLogRepository logRepo;

    @AfterReturning(value = "@annotation(recordLog)", returning = "res")
    public void after(JoinPoint point, RecordLog recordLog, Response<?> res) {

        ResponseLogEntity logEntity = new ResponseLogEntity();
        BeanUtils.copyProperties(res, logEntity);
        logEntity.setResponseData(res.getData().toString());
        logEntity.setRequestTime(res.getTime());
        logRepo.save(logEntity);

    }
}

