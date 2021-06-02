package com.mirai.lyf.bot.application.handler;

import com.mirai.lyf.bot.application.util.ResponseUtil;
import com.mirai.lyf.bot.application.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The type Unauthorized entry point.
 *
 * @author LYF on 2021-25-02
 * @description
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    /**
     * Commence.
     *
     * @param request       the request
     * @param response      the response
     * @param authException the auth exception
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, Result.error("未授权统一处理"));
    }
}
