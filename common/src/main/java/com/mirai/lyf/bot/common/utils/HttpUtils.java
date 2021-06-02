package com.mirai.lyf.bot.common.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * 线程安全的懒汉式单例
 *
 * @author LYF
 */
public class HttpUtils {
    private static volatile CloseableHttpClient HTTP_CLIENT = null;

    private HttpUtils() {
    }

    public static CloseableHttpClient instance() {
        if (HTTP_CLIENT == null) {
            synchronized (HttpUtils.class) {
                // 只需在第一次创建实例时才同步
                if (HTTP_CLIENT == null) {
                    HTTP_CLIENT = HttpClientBuilder.create().build();
                }
            }
        }
        return HTTP_CLIENT;
    }
}
