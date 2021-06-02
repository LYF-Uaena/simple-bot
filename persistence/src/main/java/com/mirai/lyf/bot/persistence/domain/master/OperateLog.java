package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * The type Operate log.
 *
 * @author LYF on 2020-09-28
 */
@Entity
@Data
@Table(name = "m_operate_logs")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class OperateLog extends MasterEntity {
    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT 'QQ群号码'"))
    private Long groupCode;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '操作人QQ号码'"))
    private Long operatorCode;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '被操作人QQ号码'"))
    private Long beOperatorCode;

    @Column(columnDefinition = ("varchar(16) DEFAULT NULL COMMENT '操作方式'"))
    private String reason;
}
