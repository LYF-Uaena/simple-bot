package com.mirai.lyf.bot.application.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DefaultPasswordEncoder
 * @Description: DefaultPasswordEncoder
 * @Author oyc
 * @Date 2021/1/18 10:58
 * @Version 1.0
 */
@Slf4j
@Component
public class DefaultPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.error("Empty encoded password");
            throw new IllegalArgumentException("encodedPassword is null");
        }
        return encodedPassword.contentEquals(rawPassword);
    }
}
