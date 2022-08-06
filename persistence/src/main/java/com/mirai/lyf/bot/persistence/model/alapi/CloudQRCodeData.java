package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网易云二维码
 *
 * @author LYF.UAENA
 * @since 2022年07月18日 23:18
 */
@NoArgsConstructor
@Data
public class CloudQRCodeData {

    @JsonProperty("key")
    private String key;
    @JsonProperty("url")
    private String url;
    @JsonProperty("qrimg")
    private String qrimg;

}
