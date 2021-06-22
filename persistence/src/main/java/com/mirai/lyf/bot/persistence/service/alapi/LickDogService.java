package com.mirai.lyf.bot.persistence.service.alapi;

import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.kit.PropertiesConstant;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.common.utils.JsonUtils;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.model.alapi.LickDogData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LYF
 * @create 2021-06-22 14:46
 * @desc 舔狗日记
 **/
@Slf4j
@Service
public class LickDogService {
    private final ConfigService configService;

    @Autowired
    public LickDogService(ConfigService configService) {
        this.configService = configService;
    }

    public Response<LickDogData> lickDog() {
        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("token", token.getValue());

        String rst = HttpUtils.post(PropertiesConstant.Api.LICK_DOG_API, params);

        @SuppressWarnings("unchecked")
        Response<LickDogData> lickDogResponse = JsonUtils.toBean(rst, Response.class, LickDogData.class);
        log.info(lickDogResponse.toString());

        return lickDogResponse;
    }
}
