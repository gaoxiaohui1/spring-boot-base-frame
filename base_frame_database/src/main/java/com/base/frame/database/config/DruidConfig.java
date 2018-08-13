//package com.base.frame.database.config;
//
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * druid链接池监控设置
// */
//@Configuration
//public class DruidConfig {
//    /**
//     * 注册一个StatViewServlet
//     *
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean startViewServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        //IP白名单
//        servletRegistrationBean.addInitParameter("allow", ips());
//        //IP黑名单
//        //servletRegistrationBean.addInitParameter("deny","10.2.123.193");
//        //控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", "base");
//        servletRegistrationBean.addInitParameter("loginPassword", "base_frame");
//        //是否能够重置数据
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//    /**
//     * IP白名单
//     *
//     * @return
//     */
//    private String ips() {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("119.90.121.65,");
//        stringBuffer.append("10.2.131.144");
//        return stringBuffer.toString();
//    }
//
//    @Bean
//    public FilterRegistrationBean statFilter() {
//
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        //添加过滤规则
//        filterRegistrationBean.addUrlPatterns("/*");
//        //忽略过滤的格式
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
//}
