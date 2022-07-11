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

import static com.mirai.lyf.bot.persistence.domain.master.GroupInfo.TABLE_NAME;

/**
 * 群基础信息
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 11:30
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class GroupInfo extends MasterEntity {
    public static final String TABLE_NAME = "m_group_info";

    @Column(name = "group_code", columnDefinition = ("varchar(16) DEFAULT NULL COMMENT 'QQ群号码'"))
    private String groupCode;

    @Column(name = "nick_name", columnDefinition = ("varchar(150) DEFAULT NULL COMMENT '群名'"))
    private String nickName;

    @Column(name = "remark", columnDefinition = ("varchar(150) comment '备注'"))
    private String remark;

}
