package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 黑白名单
 *
 * @author LYF on 2021-03-06
 */
@Entity
@Data
@Table(name = "m_rosters")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Roster extends MasterEntity {
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
