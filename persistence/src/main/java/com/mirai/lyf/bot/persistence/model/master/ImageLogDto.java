package com.mirai.lyf.bot.persistence.model.master;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

/**
 * The type Image log dto.
 *
 * @author LYF on 2021-03-03
 */
@Data
public class ImageLogDto {
    private int code;
    private String msg;
    private Long memberId;
    private String imageId;
    private String url;
    private String conclusion;
    private int conclusionType;
    private Double probability;
    private Long apiTime;
    private Long logId;
    private List<ImageLogDetail> details;
}
