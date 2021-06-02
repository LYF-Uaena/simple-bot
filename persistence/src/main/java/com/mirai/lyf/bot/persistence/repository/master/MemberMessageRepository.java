package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.MemberMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 */
@Repository
public interface MemberMessageRepository extends JpaRepository<MemberMessage, Long> {
//    /**
//     * 根据qq code和group code查询对应记录
//     *
//     * @param qqCode
//     * @param groupCode
//     * @return
//     */
//    GroupMember findByQqCodeAndGroupCode(String qqCode, String groupCode);
}
