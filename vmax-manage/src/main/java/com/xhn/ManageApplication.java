package com.xhn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: vmax
 * @description: 启动类
 * @author: mfl
 * @create: 2021-03-28 19:28
 **/
@MapperScan("com.xhn.**.mapper")
@SpringBootApplication
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);

    }
}

