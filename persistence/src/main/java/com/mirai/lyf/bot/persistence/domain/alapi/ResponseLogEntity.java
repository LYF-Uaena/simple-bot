package com.mirai.lyf.bot.persistence.domain.alapi;

import com.mirai.lyf.bot.persistence.domain.base.MasterEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.sql.Timestamp;

import static com.mirai.lyf.bot.persistence.domain.alapi.ResponseLogEntity.TABLE_NAME;

/**
 * 请求返回日志
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 22:02
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class ResponseLogEntity extends MasterEntity {
    public static final String TABLE_NAME = "m_response_log";

    @Column(name = "code", columnDefinition = ("int(3) comment '请求返回状态'"))
    private Integer code;

    @Column(name = "msg", columnDefinition = ("varchar(150) comment '请求返回信息'"))
    private String msg;

    @Column(name = "request_time", columnDefinition = ("timestamp DEFAULT NULL comment '请求api的时间'"))
    private Timestamp requestTime;

    @Column(name = "log_id", columnDefinition = ("bigint(20) DEFAULT NULL comment '本次请求log id'"))
    private Long logId;

    @Column(name = "response_data", columnDefinition = ("text DEFAULT NULL comment '请求返回数据'"))
    private String responseData;


}
