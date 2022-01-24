package com.mirai.lyf.bot.application.config;


import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * The type Druid db config.
 *
 * @author LYF on 2021-25-02
 * @description
 */
@Configuration
public class DruidDBConfig {

    private static final String DB_PREFIX = "spring.datasource";

    /**
     * 设置验证空闲连接不使用mysql的ping方法
     *
     * @since 2021/12/3 17:44
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        logger.info("init Druid Servlet Configuration ");
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
//        "/druid/*");
//        // IP白名单
//        servletRegistrationBean.addInitParameter("allow", "");
//        // IP黑名单(共同存在时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "");
//        //控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", "lyf");
//        servletRegistrationBean.addInitParameter("loginPassword", "JerryLYF0710");
//        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }

    // 解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = DB_PREFIX)
    static class IDataSourceProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;

        @Bean
        @Primary  //在同样的DataSource中，首先使用被标注的DataSource
        public DataSource dataSource() {

            DruidDataSource datasource = new DruidDataSource();
            datasource.setUrl(url);
            datasource.setUsername(username);
            datasource.setPassword(password);
            datasource.setDriverClassName(driverClassName);

            //configuration
            datasource.setInitialSize(initialSize);
            datasource.setMinIdle(minIdle);
            datasource.setMaxActive(maxActive);
            datasource.setMaxWait(maxWait);
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            datasource.setValidationQuery(validationQuery);
            datasource.setTestWhileIdle(testWhileIdle);
            datasource.setTestOnBorrow(testOnBorrow);
            datasource.setTestOnReturn(testOnReturn);
            datasource.setPoolPreparedStatements(poolPreparedStatements);
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            try {
                datasource.setFilters(filters);
            } catch (SQLException e) {
                System.err.println("druid configuration initialization filter: " + e);
            }
            datasource.setConnectionProperties(connectionProperties);
            return datasource;
        }

    }

}