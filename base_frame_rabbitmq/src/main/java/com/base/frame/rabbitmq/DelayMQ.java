package com.base.frame.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayMQ {

    @Bean
    public Exchange delayExchange() {
        return ExchangeBuilder.topicExchange("DL_EXCHANGE").durable(true).build();
    }

    @Bean
    public Queue delay10SecQueue(){
        Map<String,Object> args=new HashMap<>(2);
        args.put("x-dead-letter-exchange","FORWARD_EXCHANGE");
        return QueueBuilder.durable("delay10SecQueue")
    }

    @Bean
    public Binding delay10SecBinding(){
        return BindingBuilder.bind(delay10SecQueue()).to(delayExchange()).with()
    }
}
