package com.mirai.lyf.bot.persistence.domain.system;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * The type Config.
 *
 * @author LYF on 2021-25-02
 * @description
 */
@Entity
@Data
@Table(name = "sys_configs")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Config extends MasterEntity {
    @Column(columnDefinition = ("varchar(100) DEFAULT NULL COMMENT 'code'"))
    private String code;
    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT 'value'"))
    private String value;
}
