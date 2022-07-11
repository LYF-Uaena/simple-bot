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

import static com.mirai.lyf.bot.persistence.domain.master.MemberMessage.TABLE_NAME;

/**
 * 群员消息记录
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
public class MemberMessage extends MasterEntity {
    public static final String TABLE_NAME = "m_member_messages";

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员id'"))
    private Long memberId;

    @Column(columnDefinition = ("varchar(8000) DEFAULT NULL COMMENT '消息'"))
    private String msg;

    @Column(columnDefinition = ("varchar(100) DEFAULT NULL COMMENT '消息id'"))
    private String msgId;
}
