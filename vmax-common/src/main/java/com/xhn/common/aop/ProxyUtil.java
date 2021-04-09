package com.xhn.common.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-31 07:40
 **/
public class ProxyUtil {
    public  static <T>T getProxyOne(ClassLoader classLoader, Class<?>[] cls, InvocationHandler handler){
        return (T) Proxy.newProxyInstance(classLoader,cls,handler);
    }
}
