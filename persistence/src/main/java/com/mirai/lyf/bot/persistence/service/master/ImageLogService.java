package com.mirai.lyf.bot.persistence.service.master;

import catcode.Neko;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
import com.mirai.lyf.bot.persistence.model.alapi.ImageData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.model.master.ImageLogDto;
import com.mirai.lyf.bot.persistence.repository.master.ImageLogDetailRepository;
import com.mirai.lyf.bot.persistence.repository.master.ImageLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Image log service.
 *
 * @author LYF on 2021-02-28
 */
@Slf4j
@Service
public class ImageLogService {
    private final double VIOLATION_STANDARD = 0.85;
    private final ImageLogRepository repo;
    private final ImageLogDetailRepository detailRepository;

    @Autowired
    public ImageLogService(ImageLogRepository repo, ImageLogDetailRepository detailRepository) {
        this.repo = repo;
        this.detailRepository = detailRepository;
    }

    public ImageLog findByImageIdAndMemberId(String imageId, long memberId) {
        return repo.findByImageIdAndMemberId(imageId, memberId);
    }

    public void save(ImageLog imageLog) {
        repo.save(imageLog);
    }


    /**
     * 构建数据
     *
     * @param result     the image result
     * @param memberInfo
     */
    public void buildImageLog(Response<ImageData> result, Neko neko, ImageLogDto imageLogDto, MemberInfo memberInfo) {
        imageLogDto.setCode(result.getCode());
        imageLogDto.setMsg(result.getMsg());
        imageLogDto.setLogId(result.getLogId());
        imageLogDto.setApiTime(result.getTime());
        imageLogDto.setImageId(neko.get("id"));
        imageLogDto.setUrl(neko.get("url"));
        imageLogDto.setMemberId(memberInfo.getId());

        if (HttpCode.SUCCESS == result.getCode() && result.getData() != null) {
            imageLogDto.setConclusion(result.getData().getConclusion());
            imageLogDto.setConclusionType(result.getData().getConclusionType());
        }
    }
}


