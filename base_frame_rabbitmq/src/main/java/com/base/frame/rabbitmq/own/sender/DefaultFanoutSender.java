package com.base.frame.rabbitmq.own.sender;

import com.base.frame.model.base.MqBaseDto;
import org.springframework.stereotype.Component;

@Component
public class DefaultFanoutSender<T extends MqBaseDto> extends AbstractMqSender {
    @Override
    void beforeSend() {
        System.out.println("-------------------------------before DefaultFanoutSend-------------------------------");
    }

    @Override
    void afterSend() {
        System.out.println("-------------------------------after DefaultFanoutSend-------------------------------");
    }
}