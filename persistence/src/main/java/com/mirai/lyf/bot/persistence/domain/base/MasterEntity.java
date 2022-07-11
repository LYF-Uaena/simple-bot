package com.mirai.lyf.bot.persistence.domain.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 公共表属性父类
 *
 * @author LYF.UAENA
 * @since 2022年03月20日 11:32
 */
@Getter
@Setter
@MappedSuperclass
public class MasterEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false,
            columnDefinition = ("timestamp DEFAULT CURRENT_TIMESTAMP comment '建立时间'"))
    @CreatedDate
    private Timestamp createdTime;

    @Column(columnDefinition = ("timestamp DEFAULT NULL comment '更新时间'"))
    @LastModifiedDate
    private Timestamp modifiedTime;
}
