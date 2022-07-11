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

import static com.mirai.lyf.bot.persistence.domain.master.Roster.TABLE_NAME;

/**
 * 黑白名单
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 12:33
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class Roster extends MasterEntity {
    public static final String TABLE_NAME = "m_rosters";

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员QQ号码'"))
    private Long memberCode;

    @Column(columnDefinition = ("tinyint(1) DEFAULT 0 comment '名单类型:0：白名单 1：黑名单'"))
    private int type;

    /**
     * 黑白名单
     *
     * @author LYF on 2021-03-12
     */
    public static class Type {
        public static final int WHITE_ROSTER = 0;
        public static final int BLACK_ROSTER = 1;
    }
}
