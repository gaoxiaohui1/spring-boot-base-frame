package com.base.frame.rabbitmq.own.config;

import com.base.frame.common.tools.data.text.ToolJson;
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
        /**
         * 当消息发送到交换机（exchange）时，该方法被调用.
         *      * 1.如果消息没有到exchange,则 ack=false
         *      * 2.如果消息到达exchange,则 ack=true
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送到exchange成功: " + ToolJson.modelToJson(correlationData));
            } else {
                System.out.println("消息发送到exchange失败,原因: " + cause);
            }
        });
        /**
         * 当消息从交换机到队列失败时，该方法被调用。（若成功，则不调用）
         * 需要注意的是：该方法调用后，MsgSendConfirmCallBack中的confirm方法也会被调用，且ack = true
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("消息：{$message.messageProperties.correlationId} 发送失败, 应答码：{$replyCode} 原因：" +
                    "{$replyText} 交换机: {$exchange}  路由键: {$routingKey}");
        });
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
    public FanoutExchange defaultDeadLetterExchange() {
        return new FanoutExchange(ConstExchange.DEAD_LETTER_DEFAULT_EXCHANGE);
    }

    @Bean
    public Queue delayExecuteQueue() {
        Queue queue = new Queue(ConstQueue.DELAY_EXECUTE_QUEQUE);
        fanoutBindingMap.put(queue, defaultDeadLetterExchange());
        return queue;
    }

    @Bean
    public Queue delayMQueue() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("x-dead-letter-exchange", ConstExchange.DEAD_LETTER_DEFAULT_EXCHANGE);
        map.put("x-dead-letter-routing-key", ConstQueue.DELAY_EXECUTE_QUEQUE);
        Queue queue = new Queue(ConstQueue.DIRECT_DELAY_M_QUEQUE,
                true, false, false, map);
        directBindingMap.put(queue, defaultDirectExchange());
        return queue;
    }

    @Bean
    public Queue delayQQueue() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("x-message-ttl", 5 * 1000);
        map.put("x-dead-letter-exchange", ConstExchange.DEAD_LETTER_DEFAULT_EXCHANGE);
        map.put("x-dead-letter-routing-key", ConstQueue.DELAY_EXECUTE_QUEQUE);
        Queue queue = new Queue(ConstQueue.DIRECT_DELAY_Q_QUEQUE,
                true, false, false, map);
        directBindingMap.put(queue, defaultDirectExchange());
        return queue;
    }

    @Bean
    public List<Binding> defaultBindings() {
        List<Binding> bindingList = new ArrayList<>();
        directBindingMap.forEach((k, v) -> {
            bindingList.add(BindingBuilder.bind(k).to(v).with(k.getName()));
        });
        fanoutBindingMap.forEach((k, v) -> {
            bindingList.add(BindingBuilder.bind(k).to(v));
        });
        return bindingList;
    }
    //endregion
}
