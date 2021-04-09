package com.xhn.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @program: vmax
 * @description: 异步调用范例
 * @author: mfl
 * @create: 2021-04-09 07:36
 **/
@Component
public class AsyncDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDemo.class);

    /**
     * 最简单的异步调用，返回值为void executor.execute();
     */
    //默认情况下，Spring 使用SimpleAsyncTaskExecutor去执行这些异步方法（此执行器没有限制线程数）
    @Async("threadPoolTaskExecutor")//通过指定特定的线程池来实现方法级别覆盖
    public void asyncInvokeSimplest() {
        LOGGER.info("asyncSimplest,currentThreadName is "+ Thread.currentThread().getName());
    }

    /**
     * 带参数的异步调用 异步方法可以传入参数
     *
     * @param s
     */
    @Async
    public void asyncInvokeWithParameter(String s) {
        LOGGER.info("currentThreadName is "+Thread.currentThread().getName());
        LOGGER.info("asyncInvokeWithParameter, parementer={}", s);
    }

    /**
     * 异步调用返回Future，通过get()方法获取异步方法的返回值，等同与executor.submit()
     *
     * @param i
     * @return
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) {
        LOGGER.info("asyncInvokeReturnFuture, parementer={}", i);
        Future<String> future;
        try {
            Thread.sleep(1000 * 1);
            future = new AsyncResult<String>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }
        return future;
    }




}
