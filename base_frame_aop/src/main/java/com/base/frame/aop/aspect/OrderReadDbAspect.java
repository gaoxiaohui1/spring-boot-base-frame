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
 * 订单读库数据源拦截器
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class OrderReadDbAspect {

    /**
     * 默认数据源
     */
    @Value("${dbConfig.orderReadDBSource}")
    private String orderReadDBSource = "order_Read";

    /**
     * 设置切点
     */
    @Pointcut("@annotation(com.base.frame.aop.anation.OrderReadDbAnation)")
    private void pointcut() {
    }

    /**
     * 设置订单读库数据源
     */
    @Before("pointcut()")
    public void setOrderReadDBSourceIntercept() {
        log.info("set database connection to order_Read");
        DbContextHolder.setDBType(orderReadDBSource);
    }

    /**
     * 清除订单读库数据源
     */
    @After("pointcut()")
    public void clearOrderReadDbSourceIntercept() {
        log.info("remove database connection from order_Read");
        DbContextHolder.clearDBType();
    }
}
