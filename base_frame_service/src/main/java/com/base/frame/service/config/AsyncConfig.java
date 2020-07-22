package com.base.frame.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {
    public static final String LOG_ASYNC_EXECUTOR="logAsyncExecutor";

    public static final String ORDER_ASYNC_EXECUTOR="orderAsyncExecutor";

    public static final String USER_ASYNC_EXECUTOR="userAsyncExecutor";

    /**
     * log相关异步线程池
     * @return
     */
    @Bean(LOG_ASYNC_EXECUTOR)
    public Executor logAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        //线程名称前缀
        executor.setThreadNamePrefix("log-thread-");
        return executor;
    }

    /**
     * order相关异步线程池
     * @return
     */
    @Bean(ORDER_ASYNC_EXECUTOR)
    public Executor orderAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        //线程名称前缀
        executor.setThreadNamePrefix("order-thread-");
        return executor;
    }

    /**
     * user相关异步线程池
     * @return
     */
    @Bean(USER_ASYNC_EXECUTOR)
    public Executor userAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        //线程名称前缀
        executor.setThreadNamePrefix("user-thread-");
        return executor;
    }
}
