package com.mirai.lyf.bot.persistence.service.master;

import com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail;
import com.mirai.lyf.bot.persistence.repository.master.ImageLogDetailRepository;
import com.mirai.lyf.bot.persistence.repository.master.ImageLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Image log service.
 *
 * @author LYF on 2021-02-28
 */
@Slf4j
@Service
public class ImageLogDetailService {
    private final ImageLogDetailRepository repo;

    public ImageLogDetailService(ImageLogDetailRepository repo) {
        this.repo = repo;
    }


    public List<ImageLogDetail> saveAll(List<ImageLogDetail> details) {
        return repo.saveAll(details);
    }


}
