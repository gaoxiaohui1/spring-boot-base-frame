package com.base.frame.aop.aspect;

import com.base.frame.database.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 用户写库数据源拦截器
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class UserWriteDbAspect {

    /**
     * 默认数据源
     */
    @Value("${dbConfig.userWriteDBSource}")
    private String userWriteDBSource = "user_write";

    /**
     * 设置切点
     */
    @Pointcut("@annotation(com.base.frame.aop.anation.UserWriteDbAnation)")
    private void pointcut() {
    }

    /**
     * 设置用户写库数据源
     */
    @Before("pointcut()")
    public void setUserWriteDBSourceIntercept() {
        log.info("set database connection to user_write");
        DbContextHolder.setDBType(userWriteDBSource);
    }

    /**
     * 清除用户写库数据源
     */
    @After("pointcut()")
    public void clearUserWriteDbSourceIntercept() {
        log.info("remove database connection from user_write");
        DbContextHolder.clearDBType();
    }
}
