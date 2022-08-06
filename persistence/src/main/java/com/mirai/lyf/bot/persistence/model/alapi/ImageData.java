package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 图片api返回数据
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 21:50
 */
@Data
@NoArgsConstructor
public class ImageData {

    @JsonProperty("conclusion")
    private String conclusion;
    @JsonProperty("conclusion_type")
    private Integer conclusionType;
    @JsonProperty("data")
    private List<Reason> reasonList;

    @Data
    @NoArgsConstructor
    public static class Reason {
        @JsonProperty("level")
        private Integer level;
        @JsonProperty("msg")
        private String msg;
        @JsonProperty("probability")
        private Double probability;
    }

}
