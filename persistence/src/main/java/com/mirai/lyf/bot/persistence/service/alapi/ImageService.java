package com.mirai.lyf.bot.persistence.service.alapi;


import catcode.Neko;
import com.mirai.lyf.bot.common.kit.ConfigCodeKit;
import com.mirai.lyf.bot.common.kit.PropertiesConstant;
import com.mirai.lyf.bot.common.utils.HttpUtils;
import com.mirai.lyf.bot.common.utils.JsonUtils;
import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail;
import com.mirai.lyf.bot.persistence.domain.master.Member;
import com.mirai.lyf.bot.persistence.domain.system.Config;
import com.mirai.lyf.bot.persistence.model.alapi.ImageData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.model.master.ImageLogDto;
import com.mirai.lyf.bot.persistence.service.master.ImageLogDetailService;
import com.mirai.lyf.bot.persistence.service.master.ImageLogService;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Image service.
 *
 * @author LYF on 2021-27-02
 */
@Service
@Slf4j
public class ImageService {

    private final ConfigService configService;
    private final ImageLogService imageLogService;
    private final ImageLogDetailService imageLogDetailService;

    @Autowired
    public ImageService(ConfigService configService, ImageLogService imageLogService,
                        ImageLogDetailService imageLogDetailService) {
        this.configService = configService;
        this.imageLogService = imageLogService;
        this.imageLogDetailService = imageLogDetailService;
    }

    /**
     * 图片合规审查.
     *
     * @param neko     the neko
     * @param member   the member
     * @param imageLog the image log
     * @return the image result
     */
    public ImageLogDto verifyPicture(Neko neko, Member member, ImageLog imageLog) {
        String url = neko.get("url");
        // 查询token
        Config token = configService.find(ConfigCodeKit.ALAPI_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("token", token.getValue());
        params.put("url", url);

        String rst = HttpUtils.post(PropertiesConstant.Api.IMAGE_API, params);

        @SuppressWarnings("unchecked")
        Response<ImageData> imageResult = JsonUtils.toBean(rst, Response.class, ImageData.class);
        log.info(imageResult.toString());

        ImageLogDto imageLogDto = new ImageLogDto();
        imageLogService.buildImageLog(imageResult, neko, imageLogDto, member);

        BeanUtils.copyProperties(imageLogDto, imageLog);
        imageLogService.save(imageLog);

        List<ImageLogDetail> details = new ArrayList<>();
        long imageLogId = imageLog.getId();

        if (imageResult.getData() != null) {
            imageResult.getData().getReasonList().forEach(item -> {
                ImageLogDetail detail = new ImageLogDetail();
                detail.setImageLogId(imageLogId);
                detail.setMsg(item.getMsg());
                detail.setProbability(item.getProbability());
                detail.setLevel(item.getLevel());
                details.add(detail);
            });
            imageLogDetailService.saveAll(details);
//            imageLogDto.setDetails(details);
        }

        return imageLogDto;
    }

}
