package com.base.frame.rabbitmq.own.config;

import com.base.frame.rabbitmq.own.constant.ConstExchange;
import com.base.frame.rabbitmq.own.constant.ConstQueue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DefaultMqConfig {
    //region connection

    @Primary
    @Bean(name = "defaultRabbitMqConnectionFactory")
    public ConnectionFactory defaultRabbitMqConnectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost) {
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

    @Primary
    @Bean(name = "defaultRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory defaultListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("defaultRabbitMqConnectionFactory") ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter,
            @Value("${spring.rabbitmq.listener.simple.prefetch}") int prefetch,
            @Value("${spring.rabbitmq.listener.simple.max-concurrency}") int maxConcurrency,
            @Value("${spring.rabbitmq.listener.simple.concurrency}") int concurrency) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setMessageConverter(jackson2JsonMessageConverter);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setPrefetchCount(prefetch);
        containerFactory.setMaxConcurrentConsumers(maxConcurrency);
        containerFactory.setConcurrentConsumers(concurrency);
        configurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }


    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("defaultRabbitMqConnectionFactory") ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter,
                                         @Value("${spring.rabbitmq.template.mandatory}") Boolean mandatory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(mandatory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper xssObjectMapper) {
        return new Jackson2JsonMessageConverter(xssObjectMapper);
    }
    //endregion

    //region exchange binding queue

    private Map<Queue, DirectExchange> directBindingMap = new HashMap<>();
    private Map<Queue, FanoutExchange> fanoutBindingMap = new HashMap<>();

    @Bean
    public DirectExchange defaultDirectExchange() {
        return new DirectExchange(ConstExchange.DIRECT_DEFAULT_EXCHANGE);
    }

    @Bean
    public FanoutExchange defaultFanoutExchange() {
        return new FanoutExchange(ConstExchange.FANOUT_DEFAULT_EXCHANGE);
    }

    @Bean
    public Queue defaultDirectQueue() {
        Queue queue = new Queue(ConstQueue.DIRECT_DEFAULT_QUEQUE);
        directBindingMap.put(queue, defaultDirectExchange());
        return queue;
    }

    @Bean
    public Queue defautFanoutQueue1() {
        Queue queue = new Queue(ConstQueue.FANOUT_DEFAULT_QUEUE_1);
        fanoutBindingMap.put(queue, defaultFanoutExchange());
        return queue;
    }

    @Bean
    public Queue defautFanoutQueue2() {
        Queue queue = new Queue(ConstQueue.FANOUT_DEFAULT_QUEUE_2);
        fanoutBindingMap.put(queue, defaultFanoutExchange());
        return queue;
    }

    @Bean
    public List<Binding> defaultBindings() {
        List<Binding> bindingList = new ArrayList<>();
        directBindingMap.forEach((k, v) -> {
            bindingList.add(BindingBuilder.bind(k).to(v).withQueueName());
        });
        fanoutBindingMap.forEach((k, v) -> {
            bindingList.add(BindingBuilder.bind(k).to(v));
        });
        return bindingList;
    }
    //endregion
}
