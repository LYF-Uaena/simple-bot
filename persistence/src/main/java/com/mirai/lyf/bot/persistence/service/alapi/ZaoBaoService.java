package com.mirai.lyf.bot.persistence.service.alapi;


import com.mirai.lyf.bot.common.kit.AlApi;
import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * AlApi请求
 *
 * @author LYF on 2021-27-02
 */
@Slf4j
@Service
public class ZaoBaoService {

    private final ConfigService configService;

    @Autowired
    public ZaoBaoService(ConfigService configService) {
        this.configService = configService;
    }

    public String zaoBao() {
        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("token", token.getValue());
        params.put("format", "image");

        return HttpUtils.doHttpPost(AlApi.ZAO_BAO, params);
    }
}
