package com.base.frame.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@EnableAsync
public class EventAsyncConfig {
    public static final String EVENT_ASYNC_EXECUTOR="eventAsyncExecutor";

    @Bean(EVENT_ASYNC_EXECUTOR)
    public Executor eventAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        //线程名称前缀
        executor.setThreadNamePrefix("event-thread-");
        return executor;
    }
}
