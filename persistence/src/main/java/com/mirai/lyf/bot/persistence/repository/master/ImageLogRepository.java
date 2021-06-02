package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Group member repository.
 *
 * @author LYF on 2021-25-02
 */
@Repository
public interface ImageLogRepository extends JpaRepository<ImageLog, Long> {
    /**
     * Find by image id image log.
     *
     * @param imageId the image id
     * @return the image log
     */
    ImageLog findByImageId(String imageId);
}
