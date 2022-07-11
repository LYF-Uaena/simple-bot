package com.mirai.lyf.bot.persistence.repository.system;

import com.mirai.lyf.bot.persistence.domain.system.SysMenuGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 菜单、群中间表
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 16:17
 */
@Repository
public interface MenuGroupInfoRepository extends JpaRepository<SysMenuGroupInfo, Long> {

    SysMenuGroupInfo findByCodeAndAndGroupCode(String code, String groupCode);
}
