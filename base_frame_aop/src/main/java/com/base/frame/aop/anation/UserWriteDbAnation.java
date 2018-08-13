package com.base.frame.aop.anation;

import java.lang.annotation.*;

/**
 * 用户写库数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserWriteDbAnation {
}
