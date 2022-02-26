package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 每日60秒早报
 *
 * @author LYF
 * @since 2021-06-22 14:40
 */
@Data
@NoArgsConstructor
public class ZaoBaoData {

    @JsonProperty("date")
    private Date date;

    @JsonProperty("news")
    private List<String> news;

    @JsonProperty("weiyu")
    private String weiYu;

    @JsonProperty("image")
    private String image;

    @JsonProperty("head_image")
    private String headImage;

}
