package com.mirai.lyf.bot.persistence.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * The type Master entity.
 *
 * @author LYF on 2020-09-28
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public class MasterEntity extends IdEntity {
    @Column(nullable = false, updatable = false,
            columnDefinition = ("timestamp DEFAULT CURRENT_TIMESTAMP comment '建立时间'"))
    @CreatedDate
    private Timestamp createdTime;

    @Column(nullable = false, columnDefinition = ("timestamp DEFAULT CURRENT_TIMESTAMP comment '更新时间'"))
    @LastModifiedDate
    private Timestamp modifiedTime;
}
