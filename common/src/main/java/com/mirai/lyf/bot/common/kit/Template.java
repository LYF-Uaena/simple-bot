package com.mirai.lyf.bot.common.kit;

public class Template {
    public static final String WEATHER = "\n当前%s天气状况：\n" +
            "  %s，%s℃。%s%s\n" +
            "  最高气温: %s℃, 最低气温: %s℃\n" +
            "----------\n" +
            "空气状况：%s\n" +
            "  空气湿度：%s, 能见度：%s, 气压: %s hPa\n" +
            "----------\n" +
            "降水预报：\n" +
            "  降水概率: %s%%, \n" +
            "  24小时降水概率: %s%%";
}
