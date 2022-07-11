package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import static com.mirai.lyf.bot.persistence.domain.master.OperateLog.TABLE_NAME;

/**
 * 操作日志
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 12:34
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class OperateLog extends MasterEntity {
    public static final String TABLE_NAME = "m_operate_logs";

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT 'QQ群号码'"))
    private Long groupCode;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '操作人QQ号码'"))
    private Long operatorCode;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '被操作人QQ号码'"))
    private Long beOperatorCode;

    @Column(columnDefinition = ("varchar(16) DEFAULT NULL COMMENT '操作方式'"))
    private String reason;
}
