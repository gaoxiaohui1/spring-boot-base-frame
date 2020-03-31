package com.base.frame.rabbitmq.own.consumer;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.MqBaseDto;

public abstract class DefaultFanoutConsumer<T extends MqBaseDto> extends AbstractMqConsumer<T> {
    @Override
    void beforeConsume(MqBaseDto mqBaseDto) {
        System.out.println("-------------------------------before DefaultFanoutConsume-------------------------------");
    }

    @Override
    void afterConsume(MqBaseDto mqBaseDto, String channelAction, BaseResult processRes) {
        System.out.println("-------------------------------after DefaultFanoutConsume-------------------------------");
    }
}