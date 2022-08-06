package com.mirai.lyf.bot.persistence.annation;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 22:09
 */
@Target({
        ElementType.CONSTRUCTOR, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordLog {
}
