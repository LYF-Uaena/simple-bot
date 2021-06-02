package com.mirai.lyf.bot.application.filter;

import com.mirai.lyf.bot.application.util.JwtTokenUtil;
import com.mirai.lyf.bot.application.util.ResponseUtil;
import com.mirai.lyf.bot.application.util.Result;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The type Token authentication filter.
 *
 * @author LYF on 2021-25-02
 * @description
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    public TokenAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("=================" + request.getRequestURI());
        //不需要鉴权
        if (request.getRequestURI().indexOf("index") != -1) {
            chain.doFilter(request, response);
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(request);
        } catch (Exception e) {
            ResponseUtil.out(response, Result.error(e.getMessage()));
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(response, Result.error("鉴权失败"));
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获取Token字符串，token 置于 header 里
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }
        if (token != null && !"".equals(token.trim())) {
            // 从Token中解密获取用户名
            String userName = JwtTokenUtil.getUserNameFromToken(token);

            if (userName != null) {
                // 从Token中解密获取用户角色
                String role = JwtTokenUtil.getUserRoleFromToken(token);
                // 将ROLE_XXX,ROLE_YYY格式的角色字符串转换为数组
                String[] roles = role.split(",");
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String s : roles) {
                    authorities.add(new SimpleGrantedAuthority(s));
                }
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}
