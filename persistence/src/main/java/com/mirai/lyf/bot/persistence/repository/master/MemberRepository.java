package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Group member repository.
 *
 * @author LYF on 2021-25-02
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberInfo, Long> {
    /**
     * 根据member code和group code查询对应记录
     *
     * @param groupCode  the group code
     * @param memberCode the member code
     *
     * @return the member
     */
    MemberInfo findByGroupCodeAndMemberCode(long groupCode, long memberCode);
}
