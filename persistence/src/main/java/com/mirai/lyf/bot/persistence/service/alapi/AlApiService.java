package com.mirai.lyf.bot.persistence.service.alapi;

import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.common.utils.JsonUtils;
import com.mirai.lyf.bot.persistence.annation.RecordLog;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Al api service.
 *
 * @author LYF.UAENA
 * @since 2022年02月27日 15:50
 */
@Slf4j
@Service
public class AlApiService {
    @Autowired
    private ConfigService configService;

    @RecordLog
    public <T> Response<T> doHttpGet(String api, Map<String, String> params, Class<T> t) {
        if (CollectionUtils.isEmpty(params)) {
            params = new HashMap<>();
        }
        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        params.put("token", token.getValue());

        String rst = HttpUtils.doHttpGet(api, params);

        @SuppressWarnings("unchecked")
        Response<T> response = JsonUtils.toBean(rst, Response.class, t);
        log.info(response.toString());

        return response;
    }

}
