package com.base.frame.rabbitmq.own.sender;


import com.base.frame.model.base.MqBaseDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMqSender<T extends MqBaseDto> {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送前do something
     */
    abstract void beforeSend();

    public void send(T t) {
        beforeSend();
        rabbitTemplate.convertAndSend(t.getExChange(), t.getQueue(), t);
        afterSend();
    }

    /**
     * 发送后 do something
     */
    abstract void afterSend();
}
