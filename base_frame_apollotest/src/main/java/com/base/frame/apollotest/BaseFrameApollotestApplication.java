package com.base.frame.apollotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class BaseFrameApollotestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseFrameApollotestApplication.class, args);
    }

}
