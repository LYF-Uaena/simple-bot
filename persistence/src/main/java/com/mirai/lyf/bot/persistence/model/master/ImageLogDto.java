package com.mirai.lyf.bot.persistence.model.master;

import lombok.Data;

/**
 * The type Image log dto.
 *
 * @author LYF on 2021-03-03
 */
@Data
public class ImageLogDto {
    private Long groupCode;
    private Long qqCode;
    private String imageId;
    private String url;
    private String result;
    private int code;
    private String msg;
    private String pornConclusion;
    private String pornConfidence;
    private String politicianConclusion;
    private String politicianConfidence;
    private int politicianNum;
    private double disgustConfidence;
    private String qrCodeConclusion;
    private double qrCodeConfidence;
    private String terrorConclusion;
    private double terrorConfidence;
}
