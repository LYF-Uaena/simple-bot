package com.mirai.lyf.bot.common.kit;

/**
 * @author ：LYF
 * @date ：Created in 2020/09/19 20:00
 * @description：
 * @modified By：
 * @version: $
 */
public class PropertiesConstant {
    public static final class url {
        public final String url = "";
    }

    public static final class Api {

        public static final String IMAGE_API = "https://v2.alapi.cn/api/censor/image";

        /**
         * @api {POST,GET}
         * @param url: String
         * @description 百度云盘的分享链接支持以下格式:
         * https://pan.baidu.com/share/init?surl=*****
         * https://pan.baidu.com/wap/init?surl=*****
         * https://pan.baidu.com/s/****
         */
        public static final String BAIDU_NET_DISK_API = "https://v1.alapi.cn/api/bdpwd";
    }

}
