package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.ImageLogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageLogDetailRepository extends JpaRepository<ImageLogDetail, Long> {

}
