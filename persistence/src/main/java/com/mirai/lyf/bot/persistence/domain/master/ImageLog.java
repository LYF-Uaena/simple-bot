package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 图片log
 *
 * @author LYF on 2020-09-28
 */
@Entity
@Data
@Table(name = "m_image_logs")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ImageLog extends MasterEntity {
    @Column(nullable = false, columnDefinition = ("int(3) DEFAULT 0 COMMENT '请求返回状态code'"))
    private int code;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '请求返回状态信息'"))
    private String msg;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员id'"))
    private Long memberId;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '图片id'"))
    private String imageId;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '图片url'"))
    private String url;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '结果'"))
    private String conclusion;

    @Column(columnDefinition = ("tinyint DEFAULT 0 comment '图片结果确认程度：0：未知 1：合规 2：不合规 3：疑似'"))
    private int conclusionType;

    @Column(columnDefinition = ("double DEFAULT 0 comment '图片结果确认程度'"))
    private Double probability;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL comment '接口请求时间'"))
    private Long apiTime;

    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT 'logId'"))
    private Long logId;

    /**
     * conclusion_type
     * 是否合规,1合规，2不合规，3疑似
     */
    public static class ConclusionType {
        public static final int NON = 0;
        public static final int COMPLIANCE = 1;
        public static final int NON_COMPLIANCE = 2;
        public static final int SUSPECT = 3;
    }
}
