package com.base.frame.rabbitmq.own.consumer;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.MqBaseDto;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.rabbitmq.own.constant.ConstChannelAction;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public abstract class AbstractMqConsumer<T extends MqBaseDto> {
    /**
     * 消费前 do something
     *
     * @param t
     */
    abstract void beforeConsume(T t);

    /**
     * 具体消费逻辑处理
     *
     * @param t
     * @return
     */
    protected abstract BaseResult process(T t);

    /**
     * 消费后 do something
     *
     * @param t
     * @param channelAction
     * @param processRes
     */
    abstract void afterConsume(T t, String channelAction, BaseResult processRes);

    /**
     * 完整消费逻辑
     *
     * @param t
     * @param message
     * @param channel
     */
    @RabbitHandler
    public void consume(T t, Message message, Channel channel) {
        try {
            beforeConsume(t);
        } catch (Exception e) {
            log.error("消费前置处理异常", e);
        }

        String action = ConstChannelAction.REJECT;
        BaseResult processRes = ResultGenerator.fail("队列尚未执行");
        try {
            processRes = process(t);
            action = processRes.isSuccess() ? ConstChannelAction.ACCEPT : ConstChannelAction.REJECT;
        } catch (Exception e) {
            log.error("消费处理异常", e);
            processRes = ResultGenerator.fail("队列执行异常：" + e.getMessage());
        } finally {
            /**通过finally块来保证Ack/Nack会且只会执行一次*/
            try {
                switch (action) {
                    case ConstChannelAction.ACCEPT:
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        break;
                    default:
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                        break;
                }
            } catch (IOException e) {
                log.error("消费处理结果通知异常", e);
            }
        }

        try {
            afterConsume(t, action, processRes);
        } catch (Exception e) {
            log.error("消费后置处理异常", e);
        }
    }
}
