package com.mirai.lyf.bot.application.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Logout handler.
 *
 * @author LYF on 2021-25-02
 * @description
 */
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    /**
     * Logout.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @param authentication      the authentication
     */
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       Authentication authentication) {

    }
}
