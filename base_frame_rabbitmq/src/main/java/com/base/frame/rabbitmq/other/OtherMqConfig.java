package com.base.frame.rabbitmq.other;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 一般只消费消息
 */
@Configuration
public class OtherMqConfig {
    @Bean(name = ConstOtherMQ.OTHER_CONNECTION_FACTORY)
    public ConnectionFactory otherRabbitMqConnectionFactory(
            @Value("${spring.rabbitmq.other.host}") String host,
            @Value("${spring.rabbitmq.other.port}") int port,
            @Value("${spring.rabbitmq.other.username}") String username,
            @Value("${spring.rabbitmq.other.password}") String password,
            @Value("${spring.rabbitmq.other.virtual-host}") String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        if (port > 0) {
            connectionFactory.setPort(port);
        }
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }


    @Bean(name = ConstOtherMQ.OTHER_LISTENER_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory otherListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier(ConstOtherMQ.OTHER_CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
