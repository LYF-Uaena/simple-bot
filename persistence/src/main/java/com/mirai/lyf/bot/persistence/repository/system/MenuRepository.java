package com.mirai.lyf.bot.persistence.repository.system;

import com.mirai.lyf.bot.persistence.domain.system.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 菜单表
 *
 * @author LYF.UAENA
 * @since 2022年03月27日 14:05
 */
@Repository
public interface MenuRepository extends JpaRepository<SysMenu, Long> {

}
