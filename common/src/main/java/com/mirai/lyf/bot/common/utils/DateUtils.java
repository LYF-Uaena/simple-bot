package com.mirai.lyf.bot.common.utils;

import java.time.LocalDateTime;

/**
 * The type Date utils.
 *
 * @author LYF on 2021-03-13
 */
public class DateUtils {
    public static boolean isSameDay(LocalDateTime t1, LocalDateTime t2) {
        if (t1 == null) {
            return false;
        }
        return (t1.getYear() == t2.getYear()) && (t1.getMonthValue() == t2.getMonthValue())
                && (t1.getDayOfMonth() == t2.getDayOfMonth());
    }

    public long betweenDay(LocalDateTime t1, LocalDateTime t2) {
        return t1.toLocalDate().toEpochDay() - t2.toLocalDate().toEpochDay();
    }

    /**
     * 当前时间
     *
     * @return the local date time
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
