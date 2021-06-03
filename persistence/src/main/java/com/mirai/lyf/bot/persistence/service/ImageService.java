package com.mirai.lyf.bot.persistence.service;


import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.kit.PropertiesConstant;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.common.utils.JsonUtils;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.model.ImageResult;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Image service.
 *
 * @author LYF on 2021-27-02
 */
@Service
@Slf4j
public class ImageService {

    private final ConfigService configService;

    @Autowired
    public ImageService(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 图片合规审查.
     *
     * @param picUrl the pic url
     * @return the image result
     */
    public ImageResult verifyPicture(String picUrl) {

        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("token", token.getValue());
        params.put("url", picUrl);

        String rst = HttpUtils.post(PropertiesConstant.Api.IMAGE_API, params);
        System.out.println(rst);
        return JsonUtils.toBean(rst, ImageResult.class);
    }
}
