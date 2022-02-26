package com.mirai.lyf.bot.application.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Test controller.
 *
 * @author LYF on 2021-25-02
 * @description
 */
@RestController
public class TestController {

    @GetMapping("/druid/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping(value = {"", "welcome"})
    public String welcome() {
        return "Welcome!!!";
    }

    @GetMapping("index")
    public String index() {
        return "index!!!";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin!!!";
    }

    @GetMapping("user")
    public String user() {
        return "user!!!";
    }

    @GetMapping("customer")
    public String customer() {
        return "customer!!!";
    }

    /**
     * 方法执行前鉴权
     *
     * @return
     */
    @GetMapping("roleAdmin")
//    @Secured("ROLE_ADMIN")
    public String roleAdmin() {
        return "roleAdmin!!!";
    }

    /**
     * 方法执行前鉴权
     *
     * @return
     */
    @GetMapping("preAuthorize")
//    @PostAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String preAuthorize() {
        System.out.println("preAuthorize…………");
        return "preAuthorize!!!";
    }

    /**
     * 方法执行完再鉴权
     *
     * @return
     */
    @GetMapping("postAuthorize")
//    @PostAuthorize("hasAnyRole('ROLE_USER')")
    public String postAuthorize() {
        System.out.println("postAuthorize…………");
        return "PostAuthorize!!!";
    }
}
