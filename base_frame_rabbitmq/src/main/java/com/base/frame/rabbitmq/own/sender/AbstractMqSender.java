package com.base.frame.rabbitmq.own.sender;


import com.base.frame.model.base.MqBaseDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送抽象基类
 * @param <T>
 */
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
        if (null != t.getTtl()) {
            rabbitTemplate.convertAndSend(t.getExChange(), t.getQueue(), t, message -> {
                message.getMessageProperties().setExpiration(String.valueOf(t.getTtl()));
                return message;
            });
        } else {
            rabbitTemplate.convertAndSend(t.getExChange(), t.getQueue(), t);
        }
        afterSend();
    }

    /**
     * 发送后 do something
     */
    abstract void afterSend();
}
