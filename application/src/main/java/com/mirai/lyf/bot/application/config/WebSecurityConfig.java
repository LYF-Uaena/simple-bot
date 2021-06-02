package com.mirai.lyf.bot.application.config;

import com.mirai.lyf.bot.application.filter.TokenAuthenticationFilter;
import com.mirai.lyf.bot.application.filter.TokenLoginFilter;
import com.mirai.lyf.bot.application.handler.UnauthorizedEntryPoint;
import com.mirai.lyf.bot.application.util.DefaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 密码管理工具类
     */
    private final DefaultPasswordEncoder defaultPasswordEncoder;

    /**
     * 用户服务类
     */
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(DefaultPasswordEncoder defaultPasswordEncoder, UserDetailsService userDetailsService) {
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 配置设置，设置退出的地址和token
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                //未授权处理
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().authorizeRequests()
                .anyRequest().permitAll()
                .and().csrf().disable()
                .logout().logoutUrl("/logout");
//                .and()
//                //.addLogoutHandler(new TokenLogoutHandler(tokenManager))
//                .addFilter(new TokenLoginFilter(authenticationManager()))
//                .addFilter(new TokenAuthenticationFilter(authenticationManager())).httpBasic();
    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index**", "/api/**", "/swagger-ui.html/**");
    }
}