package com.mirai.lyf.bot.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author LYF
 * @create 2021-06-03 10:47
 * @desc 图片返回结果
 **/
@NoArgsConstructor
@Data
public class ImageResult {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("time")
    private Long time;
    @JsonProperty("log_id")
    private Long logId;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("conclusion")
        private String conclusion;
        @JsonProperty("conclusion_type")
        private Integer conclusionType;
        @JsonProperty("data")
        private List<Reason> reasonList;

        @NoArgsConstructor
        @Data
        public static class Reason {
            @JsonProperty("level")
            private Integer level;
            @JsonProperty("msg")
            private String msg;
            @JsonProperty("probability")
            private String probability;
        }
    }
}
