package com.base.frame.common.utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出excel注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportAnnotation {
    /**
     * 索引
     *
     * @return
     */
    int index() default 0;

    /**
     * 列名
     *
     * @return
     */
    String name() default "";
}
