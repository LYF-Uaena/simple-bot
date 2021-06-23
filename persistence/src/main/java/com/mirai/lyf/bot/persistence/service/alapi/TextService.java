package com.mirai.lyf.bot.persistence.service.alapi;


import com.mirai.lyf.bot.common.kit.AlApi;
import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.common.utils.JsonUtils;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.model.alapi.TextData;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 文本审核
 */
@Slf4j
@Service
public class TextService {

    private final ConfigService configService;

    @Autowired
    public TextService(ConfigService configService) {
        this.configService = configService;
    }

    public Response<TextData> verifyText(String text, Member member) {

        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("token", token.getValue());

        String rst = HttpUtils.post(AlApi.IMAGE_API, params);

        @SuppressWarnings("unchecked")
        Response<TextData> textResponse = JsonUtils.toBean(rst, Response.class, TextData.class);
        log.info(textResponse.toString());

        return textResponse;
    }

}
