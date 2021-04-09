package com.xhn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: vmax
 * @description: 线程池配置
 * @author: mfl
 * @create: 2021-04-08 21:12
 **/
@EnableAsync
@Component //可以不用实现接口，直接通过注入线程池，调用submit()或execute()方法
public class ThreadPoolConfig implements AsyncConfigurer {//通过实现AsyncConfigurer，通过重写getAsyncExecutor,使真正返回线程池成为运行带有@Async注释的方法的默认执行器:
    /**
     * 线程池维护线程的最小数量
     */
    private final int corePoolSize = 5;
    /**
     * 线程池维护线程的最大数量
     */
    private final int maxPoolSize = 50;
    /**
     * 队列最大长度
     */
    private final int queueCapacity = 100;
    /**
     * 保持时间
     */
    private final int keepAliveSeconds = 60 * 60 * 2;

    /**
     * 线程前缀
     */
    private final String threadNamePrefix = "VMAX-AUTH-";


    //初始化线程池
    @Bean//是按方法名来注入的
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//如果任务被拒绝了，则由调用线程（提交任务的线程）直接执行此任务
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor();
    }

   /* *//**
     * 异常处理todo
     * @return
     *//*
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }*/

}
