package com.mirai.lyf.bot.common.kit;

/**
 * @author ：LYF
 * @date ：Created in 2020/5/20 21:46
 * @description：
 * @modified By：
 * @version: $
 */
public final class ConstantKit {

    public static final class ReturnCode {
        public static final int SUCCESS = 200;
        public static final int SIZE_ERROR = 422;
    }

    public static final class ReturnStatus {
        public static final String SUCCESS = "success";
        public static final String FAILED = "failed";
    }

    public static final class ImageType {
        /**
         * 通用文字识别
         */
        public static final String OCR = "ocr";
        /**
         * 公众人物识别
         */
        public static final String PUBLIC = "public";
        /**
         * 政治人物识别
         */
        public static final String POLITICIAN = "politician";
        /**
         * 色情识别
         */
        public static final String ANTIPORN = "antiporn";
        /**
         * 暴恐识别
         */
        public static final String TERROR = "terror";
        /**
         * 网图OCR识别
         */
        public static final String WEBIMAGE = "webimage";
        /**
         * 恶心图
         */
        public static final String DISGUST = "disgust";
        /**
         * 水印、二维码
         */
        public static final String WATERMARK = "watermark";
    }

    public static final class RegxPattern {
        public static final String HTTP_HTTPS = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";
    }


    public static final class Remarks {
        /**
         * 踢出
         */
        public static final String KICK = "KICK";
        /**
         * 退群
         */
        public static final String LEAVE = "LEAVE";
        /**
         * 禁言
         */
        public static final String BAN = "BAN";
        /**
         * 解除禁言
         */
        public static final String LIFT_BAN = "LIFT_BAN";
        /**
         * 加群
         */
        public static final String APPROVE = "APPROVE";
    }
}
