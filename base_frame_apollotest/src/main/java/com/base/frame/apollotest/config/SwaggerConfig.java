package com.base.frame.apollotest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置环境
     */
    @Value("${spring.profiles.active}")
    String ProAction = "dev";

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.base.frame.apollotest.controller"))
                .paths(input -> {
                    if ("pro".equals(ProAction.toLowerCase())) {
                        return false;
                    } else {
                        return true;
                    }
                })
                .build();

        return docket;
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        String ip = "";
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (address != null) {
            ip = address.getHostName() + "-" + address.getCanonicalHostName() + "; profile:" + ProAction;
        }
        ApiInfo apiInfo = new ApiInfo("系统微服务手册", "此在线API手册为调用微服务技术人员提供开发参考", "1.0.0"
                , "Terms of service", new Contact(" 服务器信息: " + ip, "", ""),
                "测试", "http://www.test.com/");
        return apiInfo;
    }
}
