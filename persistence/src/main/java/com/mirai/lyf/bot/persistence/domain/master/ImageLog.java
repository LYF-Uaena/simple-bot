package com.mirai.lyf.bot.persistence.domain.master;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * The type Operate log.
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
    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT '群员id'"))
    private Long memberId;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '图片id'"))
    private String imageId;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '图片url'"))
    private String url;

    @Column(columnDefinition = ("varchar(300) DEFAULT NULL COMMENT '结果'"))
    private String result;

    @Column(nullable = false, columnDefinition = ("int(3) DEFAULT 0 COMMENT '请求返回状态code'"))
    private int code;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '请求返回状态信息'"))
    private String msg;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '色情类鉴定类别'"))
    private String pornConclusion;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '色情类鉴定结果'"))
    private String pornConfidence;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '政治人物类鉴定结果'"))
    private String politicianConclusion;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '政治人物类鉴定可信度'"))
    private String politicianConfidence;

    @Column(columnDefinition = ("int(11) DEFAULT 0 COMMENT '政治人物类鉴定数量'"))
    private int politicianNum;

    @Column(columnDefinition = ("decimal(19,16) DEFAULT 0 COMMENT '恶心图片可信度'"))
    private double disgustConfidence;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '二维码图片结果'"))
    private String qrCodeConclusion;

    @Column(columnDefinition = ("decimal(19,16) DEFAULT 0 COMMENT '二维码图片可信度'"))
    private double qrCodeConfidence;

    @Column(columnDefinition = ("varchar(32) DEFAULT NULL COMMENT '暴恐图片可信度'"))
    private String terrorConclusion;

    @Column(columnDefinition = ("decimal(19,16) DEFAULT 0 COMMENT '暴恐图片可信度'"))
    private double terrorConfidence;
}
