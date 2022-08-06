package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 舔狗日记
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 21:50
 */
@NoArgsConstructor
@Data
public class LickDogData {

    @JsonProperty("content")
    private String content;

}
