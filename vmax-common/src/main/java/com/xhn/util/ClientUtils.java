package com.xhn.util;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: vmax
 * @description: 用户信息工具类
 * @author: mfl
 * @create: 2021-04-02 19:48
 **/
public class ClientUtils {
    ThreadLocal<String> pool = new ThreadLocal();

    @Autowired

    public void getCurrentUserId(){
        //pool.get()
    }

}
