package com.base.frame.aop.anation;

import java.lang.annotation.*;

/**
 * 访问日志记录注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLogAnation {
}
