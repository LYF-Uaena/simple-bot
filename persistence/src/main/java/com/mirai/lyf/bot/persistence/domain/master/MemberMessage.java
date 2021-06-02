package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


/**
 * @author LYF
 */
@Entity
@Data
@Table(name = "m_member_messages")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class MemberMessage extends MasterEntity {
    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员id'"))
    private Long memberId;

    @Column(columnDefinition = ("varchar(8000) DEFAULT NULL COMMENT '消息'"))
    private String msg;
}
