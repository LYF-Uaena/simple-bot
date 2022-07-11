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

import static com.mirai.lyf.bot.persistence.domain.system.SysMenu.TABLE_NAME;


/**
 * 菜单
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 15:44
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class SysMenu extends MasterEntity {
    public static final String TABLE_NAME = "sys_menu";

    @Column(columnDefinition = ("varchar(100) not null COMMENT '功能代号'"))
    private String code;

    @Column(columnDefinition = ("varchar(100) not null COMMENT '功能名称'"))
    private String name;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT 'remark'"))
    private String remark;
}
