package com.mirai.lyf.bot.common.utils;

import java.util.regex.Pattern;

/**
 * @author ：LYF
 * @date ：Created in 2020/5/29 21:58
 */
public class PatternUtil {
    private PatternUtil() {
    }

    private static class groupAddInstance {
        private static final Pattern group = Pattern.compile("netbian|NETBIAN");
    }

    public static Pattern getGroupAddInstance() {
        return groupAddInstance.group;
    }

}
