package com.base.frame.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.base.frame")
@MapperScan("com.base.frame.dao")
@EnableTransactionManagement
public class BaseFrameApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseFrameApiApplication.class, args);
    }
}
