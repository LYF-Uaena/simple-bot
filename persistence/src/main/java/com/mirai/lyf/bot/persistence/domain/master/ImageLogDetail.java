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

import static com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail.TABLE_NAME;

/**
 * 图片检测结果详情
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
public class ImageLogDetail extends MasterEntity {
    public static final String TABLE_NAME = "m_image_log_details";

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
