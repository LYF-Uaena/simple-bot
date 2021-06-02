package com.mirai.lyf.bot.persistence.repository.master;

import com.mirai.lyf.bot.persistence.domain.master.Roster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Roster repository.
 *
 * @author LYF on 2021-03-10
 */
@Repository
public interface RosterRepository extends JpaRepository<Roster, Long> {

    /**
     * Find by member code roster.
     *
     * @param code the code
     * @return the roster
     */
    Roster findByMemberCode(long code);
}
