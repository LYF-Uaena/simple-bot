package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LYF
 * @create 2021-06-23 09:25
 * @desc 文本审核
 **/
@NoArgsConstructor
@Data
public class TextData {

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
        @JsonProperty("words")
        private List<String> words;
    }

}
