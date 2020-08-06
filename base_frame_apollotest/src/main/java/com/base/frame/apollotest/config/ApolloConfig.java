package com.base.frame.apollotest.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig(value = {"base.frame"}, order = 1)
public class ApolloConfig {
    @Configuration
    @EnableApolloConfig(order = 2)
    public class ApolloApplicationConfig {

    }
}
