package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文本审核
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 21:51
 */
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
