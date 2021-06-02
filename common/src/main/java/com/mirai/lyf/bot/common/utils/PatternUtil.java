package com.mirai.lyf.bot.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：LYF
 * @date ：Created in 2020/5/29 21:58
 */
public class PatternUtil {

    private static final PatternUtil PATTERN_UTIL = new PatternUtil();

    public static PatternUtil build() {
        return PATTERN_UTIL;
    }

    private PatternUtil() {
    }

    public static boolean stringPattern(String regx, String str) {
        Pattern r = Pattern.compile(regx);
        Matcher m = r.matcher(str);
        return m.find();
    }
}
