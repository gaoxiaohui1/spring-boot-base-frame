package com.base.frame.database.config;


import lombok.Data;

/**
 * 数据库配置
 */
@Data
public class DbItem {
    /**
     * 数据源名称
     */
    private String dbSource;
    /**
     * 数据库类型（sqlserver）
     */
    private String dbType;
    /**
     * 数据库IP（127.0.0.1）
     */
    private String dbAddr;
    /**
     * 数据库端口（1433）
     */
    private String dbPort;
    /**
     * 数据库名（order_test）
     */
    private String dbName;
    /**
     * 账号（admin）
     */
    private String dbUserName;
    /**
     * 密码（admin）
     */
    private String dbPw;
    /**
     * 驱动名称
     */
    private String driver;

    public String getDriver() {
        switch (this.dbType.toLowerCase()) {
            case "mysql":
                return "com.mysql.jdbc.Driver";
            case "sqlserver":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case "oracle":
                return "oracle.jdbc.driver.OracleDriver";
            default:
                return "";
        }
    }

    /**
     * jdbc地址
     */
    private String jdbcUrl;

    public String getJdbcUrl() {
        switch (this.dbType.toLowerCase()) {
            case "mysql":
                return String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true", this.dbAddr, this.dbPort, this.dbName);
            case "sqlserver":
                return String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s", this.dbAddr, this.dbPort, this.dbName);
            case "oracle":
                return String.format("jdbc:oracle:thin:@%s:%s:%s", this.dbAddr, this.dbPort, this.dbName);
            default:
                throw new RuntimeException("not support jdbc driver");
        }
    }
}
