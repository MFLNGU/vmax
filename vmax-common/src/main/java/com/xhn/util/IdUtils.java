package com.xhn.util;

import com.xhn.util.common.IDWorker;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author: 86188
 * @date: 2021/3/25
 * @desc
 */
@Slf4j
public class IdUtils {
    private static IDWorker randomSqlId = new IDWorker();

    /**
     * 雪花算法获取随机id(支持分布式)
     *
     * @return
     */
    public static Long getId() {
        try {
            return randomSqlId.nextId();
        } catch (Exception e) {
            //if(log.e)
            log.error("随机ID生成失败", e.getMessage());
        }
        return 0L;
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUUID(){
        return  UUID.randomUUID().toString().replace("-","");
    }
}
