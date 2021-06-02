package com.mirai.lyf.bot.persistence.domain.base;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * id 's entity
 *
 * @author Created by LYF on 2020/09/24
 */
@MappedSuperclass
@Data
public class IdEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
