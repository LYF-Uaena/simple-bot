package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LYF
 * @create 2021-06-22 10:20
 * @desc API请求返回实体
 **/
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<K> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String msg;

    // 请求api的时间
    @JsonProperty("time")
    private Long time;

    // 本次请求log id
    @JsonProperty("log_id")
    private Long logId;

    // 请求返回数据
    @JsonProperty("data")
    private K data;
    
}
