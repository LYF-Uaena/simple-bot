package com.mirai.lyf.bot.persistence.domain.system;

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

import static com.mirai.lyf.bot.persistence.domain.system.Config.TABLE_NAME;

/**
 * 系统配置
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 12:28
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class Config extends MasterEntity {
    public static final String TABLE_NAME = "sys_configs";

    @Column(columnDefinition = ("varchar(100) DEFAULT NULL COMMENT 'code'"))
    private String code;
    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT 'value'"))
    private String value;
}
