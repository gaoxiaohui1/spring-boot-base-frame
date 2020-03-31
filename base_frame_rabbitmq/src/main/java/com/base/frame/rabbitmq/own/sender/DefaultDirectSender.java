package com.base.frame.rabbitmq.own.sender;

import com.base.frame.model.base.MqBaseDto;
import org.springframework.stereotype.Component;

@Component
public class DefaultDirectSender<T extends MqBaseDto> extends AbstractMqSender {
    @Override
    void beforeSend() {
        System.out.println("-------------------------------before DefaultDirectSend-------------------------------");
    }

    @Override
    void afterSend() {
        System.out.println("-------------------------------after DefaultDirectSend-------------------------------");
    }
}
