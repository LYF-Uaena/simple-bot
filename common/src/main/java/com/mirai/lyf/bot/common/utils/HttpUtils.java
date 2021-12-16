package com.mirai.lyf.bot.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 线程安全的懒汉式单例
 *
 * @author LYF
 */
public class HttpUtils {
    private static volatile CloseableHttpClient HTTP_CLIENT = null;

//    private HttpUtils() {
//    }

//    public static CloseableHttpClient instance() {
//        if (HTTP_CLIENT == null) {
//            synchronized (HttpUtils.class) {
//                // 只需在第一次创建实例时才同步
//                if (HTTP_CLIENT == null) {
//                    HTTP_CLIENT = HttpClientBuilder.create().build();
//                }
//            }
//        }
//        return HTTP_CLIENT;
//    }

    /**
     * post 请求
     *
     * @param api  请求的api
     * @param list 请求参数
     * @return the object
     */
    @NotNull
    public static String post(String api, Map<String, String> list) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
        HttpPost httpPost = new HttpPost(api);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");

        UrlEncodedFormEntity entityParam = null;

        try {
            List<NameValuePair> params = new ArrayList<>();
            list.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
            entityParam = new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(entityParam);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)

        // 响应模型
        CloseableHttpResponse response = null;
        String rst = "";
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                rst = EntityUtils.toString(responseEntity);
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
        return rst;
    }
}
