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
        /**
         * @api {POST,GET}
         * @param url: String
         * 图片url
         * @param file: String
         * 图片base64
         * @param type: String
         * 识别类型，默认porn；
         * 支持组合，如：type=public,antiporn，多个类型用英文逗号分隔
         * 所有类型：
         * ocr	        通用文字识别
         * public	    公众人物识别
         * politician	政治人物识别
         * antiporn	    色情识别
         * terror	    暴恐识别
         * webimage	    网图OCR识别
         * disgust      恶心图
         * watermark    水印、二维码
         * @description 图片仅支持4MB以下
         */
        public static final String IMAGE_API = "https://v1.alapi.cn/api/ai/image";

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
