package com.mirai.lyf.bot.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量分批查询工具类
 *
 * @author LYF_UAENA
 * @since 2023年06月17日 23:38
 */
@Slf4j
public class BatchQueryUtils {

    /**
     * 获取查询结果集
     */
    public interface QueryListInterface<V, U> {
        List<V> getList(List<U> params, Object... objects);
    }

    /**
     * 获取查询结果集
     *
     * @param <T>                返回值类型
     * @param <U>                in参数类型
     * @param params             in参数
     * @param flag               标记，用于日志记录
     * @param queryListInterface 查询的方法
     * @return 数据集合
     */
    public <T, U> List<T> getList(List<U> params, String flag, QueryListInterface<T, U> queryListInterface) {
        log.info("批量查询开始: {}", flag);

        // 分批处理数据
        int batchSize = 10;
        int size = params.size();
        int batchTimes = (size + batchSize - 1) / batchSize;

        List<T> rst = new ArrayList<>();
        try {
            for (int i = 0; i < batchTimes; i++) {
                int end = (i + 1) * batchSize;
                log.info("{}, 批量查询第{}次", flag, i);
                List<U> subList = params.subList(i * batchSize, Math.min(end, size));
                List<T> list = queryListInterface.getList(subList);
                rst.addAll(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

}
