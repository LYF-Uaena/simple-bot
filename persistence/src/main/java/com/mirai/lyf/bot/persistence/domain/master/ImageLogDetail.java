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
 * @author LYF
 * @create 2021-06-03 14:42
 * @desc
 **/
@Entity
@Data
@Table(name = "m_image_log_details")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ImageLogDetail extends MasterEntity {
    @Column(columnDefinition = ("bigint(20) DEFAULT NULL COMMENT 'imageLogId'"))
    private Long imageLogId;

    @Column(columnDefinition = ("tinyint DEFAULT 0 comment '图片违规类别：1违规色情,2恐怖暴力,3恶心图,4广告,5政治敏感,6敏感旗帜,7违规图文,8其他违规," +
            "9不良场景'"))
    private int level;

    @Column(columnDefinition = ("varchar(100) DEFAULT NULL COMMENT '违规信息'"))
    private String msg;

    @Column(columnDefinition = ("varchar(200) DEFAULT NULL COMMENT '提示'"))
    private Double probability;

    /**
     * 图片违规类别
     * 1违规色情,2恐怖暴力,3恶心图,4广告,5政治敏感,6敏感旗帜,7违规图文,8其他违规,9不良场景
     */
    public static class Level {
        public static final int PORN = 1;
        public static final int TERROR = 2;
        public static final int DISGUST = 3;
        public static final int AD = 4;
        public static final int POLITICAL = 5;
        public static final int SENSITIVE_FLAG = 6;
        public static final int GRAPHIC = 7;
        public static final int OTHER = 8;
        public static final int BAD_SCENE = 9;
    }
}
