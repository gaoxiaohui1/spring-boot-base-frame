package com.base.frame.aop.aspect;

import com.base.frame.aop.anation.DbAnation;
import com.base.frame.database.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(0)
@Component
public class DbAspect {

    @Before("@annotation(dbAnation)")
    public void beforeProcess(DbAnation dbAnation) {
        log.warn("set database connection to " + dbAnation.dbType());
        DbContextHolder.setDBType(dbAnation.dbType());
    }


    @After("@annotation(dbAnation)")
    public void afterProcess(DbAnation dbAnation) {
        log.warn("remove database connection from " + dbAnation.dbType());
        DbContextHolder.clearDBType();
    }
}
