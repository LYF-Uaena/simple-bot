package com.mirai.lyf.bot.persistence.service.master;

import com.mirai.lyf.bot.persistence.domain.master.MemberInfo;
import com.mirai.lyf.bot.persistence.repository.master.MemberRepository;
import com.mirai.lyf.bot.persistence.service.CommonService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Group member service.
 *
 * @author LYF on 2021-25-02
 * @description
 */
@Service
public class MemberService extends CommonService<MemberInfo, MemberRepository> {

    @Autowired
    public MemberService(MemberRepository repo) {
        super(repo);
    }

    /**
     * Find by group code and qq code member.
     *
     * @param groupCode  the group code
     * @param memberCode the member code
     *
     * @return the member
     */
    public MemberInfo findByGroupCodeAndQqCode(@NotNull Long groupCode, @NotNull Long memberCode) {
        return repo.findByGroupCodeAndMemberCode(groupCode, memberCode);
    }

    /**
     * Find by group code and qq code member.
     *
     * @param groupCode  the group code
     * @param memberCode the member code
     *
     * @return the member
     */
    public MemberInfo isExist(@NotNull Long groupCode, @NotNull Long memberCode) {
        return repo.findByGroupCodeAndMemberCode(groupCode, memberCode);
    }

}
