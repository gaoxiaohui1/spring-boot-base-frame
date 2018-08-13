package com.base.frame.database;

import org.springframework.beans.factory.annotation.Value;

/**
 * 数据源上下文
 */
public class DbContextHolder {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    /**
     * 默认数据源
     */
    @Value("${dbConfig.defaultDBSource}")
    private static String defaultDBSource = "user_read";

    /**
     * 设置数据源上下文
     *
     * @param dbType
     */
    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源上下文
     *
     * @return
     */
    public static String getDBType() {
        return contextHolder.get() == null ? defaultDBSource : contextHolder.get();
    }

    /**
     * 清除数据源上下文
     */
    public static void clearDBType() {
        contextHolder.remove();
    }
}
