package com.mirai.lyf.bot.persistence.service;


import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.kit.PropertiesConstant;
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
        String category = "";
        Config config = configService.find(ConfigCodeKit.PIC_CATEGORY);
        Config tokenConfig = configService.find(ConfigCodeKit.ALAPI_KEY);
        if (config != null) {
            category = config.getValue();
        }
        ImageResult returnValue = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
        // 创建Post请求
        String params = "token=" + tokenConfig.getValue() + "&type=" + category + "&url=" + picUrl;
        /**
         * params = URLEncoder.encode(params, "utf-8");
         */

        HttpPost httpPost = new HttpPost(PropertiesConstant.Api.IMAGE_API + "?" + params);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                String rst = EntityUtils.toString(responseEntity);
                returnValue = JsonUtils.toBean(rst, ImageResult.class);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }
}
