package com.mirai.lyf.bot.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
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
     *
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

    private static final CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 总连接池数量
        connectionManager.setMaxTotal(1500);
        // 可为每个域名设置单独的连接池数量
        // connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("127.0.0.1")), 500);
        connectionManager.setDefaultMaxPerRoute(1);  // 这个必须设置，默认2，也就是单个路由最大并发数是2

        // setTcpNoDelay 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
        // setSoReuseAddress 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
        // setSoLinger 关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
        // setSoTimeout 接收数据的等待超时时间，单位ms
        // setSoKeepAlive 开启监视TCP连接是否有效
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoReuseAddress(true)
                .setSoLinger(60)
                .setSoTimeout(500)
                .setSoKeepAlive(true)
                .build();
        connectionManager.setDefaultSocketConfig(socketConfig);

        // setConnectTimeout表示设置建立连接的超时时间
        // setConnectionRequestTimeout表示从连接池中拿连接的等待超时时间
        // setSocketTimeout表示发出请求后等待对端应答的超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        // 重试处理器，StandardHttpRequestRetryHandler这个是官方提供的，看了下感觉比较挫，很多错误不能重试，可自己实现HttpRequestRetryHandler接口去做
//        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();
        // 关闭重试策略
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);

        // 自定义请求存活策略
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            /**
             * 返回时间单位是毫秒
             */
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                return 60 * 1000;
            }
        };

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(requestRetryHandler)
                .setKeepAliveStrategy(connectionKeepAliveStrategy)
                .build();
    }

    /**
     * httpclient get
     *
     * @param uri       请求地址
     * @param getParams 请求参数
     *
     * @return
     */
    public static String doHttpGet(String uri, Map<String, String> getParams) {
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            if (null != getParams && !getParams.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : getParams.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                uriBuilder.setParameters(list);
            }
            httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            log.error("CloseableHttpClient-get-请求异常", e);
        } finally {
            try {
                if (null != response)
                    response.close();
                if (null != httpGet)
                    httpGet.releaseConnection();
            } catch (Exception e) {
                log.error("CloseableHttpClient-post-请求异常,释放连接异常", e);
            }
        }
        return "";
    }

    /**
     * httpclient post
     *
     * @param uri       请求地址
     * @param getParams map化的请求体对象
     *
     * @return
     */
    public static String doHttpPost(String uri, Map<String, String> getParams) {
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(uri);
            if (null != getParams && !getParams.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : getParams.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(list, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            log.error("CloseableHttpClient-post-请求异常", e);
        } finally {
            try {
                if (null != response)
                    response.close();
                if (null != httpPost)
                    httpPost.releaseConnection();
            } catch (Exception e) {
                log.error("CloseableHttpClient-post-请求异常,释放连接异常", e);
            }
        }
        return "";
    }

    /**
     * httpclient post
     *
     * @param uri       请求地址
     * @param reqParams json串
     *
     * @return
     */
    public static String doHttpPost(String uri, String reqParams) {
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(uri);
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            if (StringUtils.isNotBlank(reqParams)) {
                StringEntity postingString = new StringEntity(reqParams, "utf-8");
                httpPost.setEntity(postingString);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            log.error("CloseableHttpClient-post-请求异常", e);
        } finally {
            try {
                if (null != response)
                    response.close();
                if (null != httpPost)
                    httpPost.releaseConnection();
            } catch (Exception e) {
                log.error("CloseableHttpClient-post-请求异常,释放连接异常", e);
            }
        }
        return "";
    }
}
