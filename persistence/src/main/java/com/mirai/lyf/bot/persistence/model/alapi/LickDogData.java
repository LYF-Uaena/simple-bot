package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LYF
 * @create 2021-06-22 14:40
 * @desc 舔狗日记
 **/
@NoArgsConstructor
@Data
public class LickDogData {

    @JsonProperty("content")
    private String content;

}
