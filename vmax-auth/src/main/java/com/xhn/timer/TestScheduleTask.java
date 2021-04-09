package com.xhn.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: vmax
 * @description: 定时任务测试
 * @author: mfl
 * @create: 2021-03-28 17:53
 **/
@Component
@EnableScheduling
public class TestScheduleTask {

    @Scheduled(cron = "0 0/1 * ? * ?")
    public void testTask(){
        System.out.println("定时任务开启");
    }
}
