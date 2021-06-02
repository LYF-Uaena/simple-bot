package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 群成员
 *
 * @author LYF on 2021-03-14
 */
@Entity
@Data
@Table(
        name = "m_members",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_m_members_group_code_member_code", columnNames = {"group_code", "member_code"})
        },
        indexes = {
                @Index(name = "uk_m_members_group_code_member_code", columnList = "group_code, member_code")
        }
)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Member extends MasterEntity {
    @Column(name = "group_code", columnDefinition = ("bigint(20) DEFAULT NULL COMMENT 'QQ群号码'"))
    private Long groupCode;

    @Column(name = "member_code", columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员QQ号码'"))
    private Long memberCode;

    @Column(columnDefinition = ("varchar(150) DEFAULT NULL COMMENT '昵称'"))
    private String nickName;

    @Column(columnDefinition = ("varchar(300) comment '头像路径'"))
    private String headUrl;

    @Column(columnDefinition = ("varchar(150) comment '备注'"))
    private String remark;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '身份'"))
    private String identity;

    @Column(columnDefinition = ("timestamp NULL DEFAULT NULL comment '最后发言时间'"))
    private LocalDateTime lastSpeakTime;

    @Column(columnDefinition = ("tinyint(1) DEFAULT 0 comment '账号状态：0：在群 1：主动离群 2：踢出'"))
    private int status;

    /**
     * 成员状态
     *
     * @author LYF on 2021-03-14
     */
    public static class Status {
        public static final int IN_GROUP = 0;
        public static final int LEAVE = 1;
        public static final int KICK = 2;
    }
}
