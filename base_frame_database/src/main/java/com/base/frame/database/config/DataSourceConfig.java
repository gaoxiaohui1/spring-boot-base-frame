package com.base.frame.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.common.tools.data.text.ToolStr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import sun.misc.IOUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源设置
 */
@Slf4j
@Configuration
public class DataSourceConfig {

    /**
     * 所有数据源信息json
     */
    @Value("classpath:META-INF/dbConfig.json")
    private Resource dbResource;

    /**
     * 默认数据源
     */
    @Value("${dbConfig.defaultDBSource}")
    private String defaultDBSource = "user_read";

    /**
     * druid连接池设置：默认为stat,即开启sql监控
     */
    @Value("${spring.datasource.druid.filters}")
    private String druidFilters = "stat";

    /**
     * 数据源类型
     */
    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";


    @PostConstruct
    public void init() {
        if (!ToolStr.isSpace(druidFilters)) {
            System.setProperty("druid.filters", druidFilters);
            log.info("Set System Property: druid.filters:" + druidFilters);
        }
        //开启空闲线程检测（getConnection时，计算连接的空闲时间，超过TimeBetweenEvictionRunsMillis会使用该连接执行validationQuery验证连接是否可用,如不可用，将抛弃改连接，取下一个连接）,并不能完全屏蔽错误连接的返回，
        System.setProperty("druid.testWhileIdle", "true");
        System.setProperty("druid.validationQuery", "select 1");
        //取消testOnBorrow，该值标识每次getConnection时都进行检测，比较影响性能
        System.setProperty("druid.testOnBorrow", "false");

    }

    /**
     * 创建数据源
     *
     * @param dbItem
     * @return
     */
    private DataSource buildDataSource(DbItem dbItem) {
        if (dbItem == null) {
            throw new RuntimeException("buildDataSource 参数dbItem 为空");
        }
        try {
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) DATASOURCE_TYPE_DEFAULT);

            DataSourceBuilder factory = DataSourceBuilder.create()
                    .driverClassName(dbItem.getDriver())
                    .url(dbItem.getJdbcUrl())
                    .username(dbItem.getDbUserName())
                    .password(dbItem.getDbPw())
                    .type(dataSourceType);

            DataSource dataSource = factory.build();

            //region 连接池设置
            if (dataSource instanceof DruidDataSource) {
                //配置初始化大小、最小、最大
                ((DruidDataSource) dataSource).setInitialSize(5);

                //最小连接池数量
                ((DruidDataSource) dataSource).setMinIdle(1);

                //最大连接池数量
                ((DruidDataSource) dataSource).setMaxActive(20);

                //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，
                //并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
                ((DruidDataSource) dataSource).setMaxWait(10 * 1000);

                //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
                //如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
                ((DruidDataSource) dataSource).setTestWhileIdle(true);

                //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
                ((DruidDataSource) dataSource).setTestOnBorrow(false);

                //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
                ((DruidDataSource) dataSource).setTestOnReturn(false);

                /**
                 * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                 * 有两个含义：
                 1) Destroy线程会检测连接的间隔时间
                 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
                 * 1.testWhileIdle的空闲时间判断依据
                 * 2.destroyThread检查的间隔时间（为什么这两个会用一个时间，大概是testWhileIdle进行主动检测后，
                 *    destroy的间隔就没必要小于这个时间了。destroy 依赖 min/mixEvictableIdleTimeMillis 时间进行链接驱逐)
                 */
                ((DruidDataSource) dataSource).setTimeBetweenEvictionRunsMillis(60 * 1000);

                //配置一个连接在池中最小生存的时间，单位是毫秒
                ((DruidDataSource) dataSource).setMinEvictableIdleTimeMillis(300 * 1000);

                //连接检查超时，如果执行检查sql超过这个时间认为失败
                ((DruidDataSource) dataSource).setValidationQueryTimeout(2000);
                ((DruidDataSource) dataSource).setValidationQuery("select 1");


                log.info("构建datasource:" + ((DruidDataSource) dataSource).getRawJdbcUrl());
            }
            //endregion

            return dataSource;
        } catch (ClassNotFoundException e) {
            log.error("buildDataSource报错", e);
            return null;
        }
    }

    /**
     * 设置动态数据源
     */
    @Primary
    @Bean
    public DynamicDataSource dynamicDataSource() {
        try {
            DynamicDataSource dynamicDataSource = new DynamicDataSource();
            //所有数据源
            Map<Object, Object> targetDataSources = new HashMap<>();
            List<DbItem> dbItemList = getDbItemList();
            if (dbItemList == null || dbItemList.size() == 0) {
                throw new RuntimeException("dbConfig.dbSourceTotalInfo 数据源配置转换失败");
            }
            dbItemList.forEach(x -> {
                DataSource dataSource = buildDataSource(x);
                targetDataSources.put(x.getDbSource(), dataSource);
                if (x.getDbSource().equals(defaultDBSource)) {
                    log.info("设置默认数据源");
                    dynamicDataSource.setDefaultTargetDataSource(dataSource);
                }
            });
            dynamicDataSource.setTargetDataSources(targetDataSources);
            return dynamicDataSource;
        } catch (Exception e) {
            log.error("dynamicDataSource报错", e);
            throw e;
        }
    }

    /**
     * 获取数据源配置
     *
     * @return
     */
    private List<DbItem> getDbItemList() {
        String dbSourceTotalInfo="";
        try {
            dbSourceTotalInfo = new String(IOUtils.readFully(dbResource.getInputStream(), -1,true));
        }catch (IOException e){
            throw new IllegalArgumentException("dbConfig.dbSourceTotalInfo 数据源配置为空");
        }
        List<DbItem> dbItemList = ToolJson.jsonToModelList(dbSourceTotalInfo, DbItem.class);
        return dbItemList;
    }
}
