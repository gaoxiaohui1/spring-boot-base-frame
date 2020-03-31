package com.base.frame.consumer.other;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.rabbitmq.other.ConstOtherMQ;
import com.base.frame.rabbitmq.own.constant.ConstChannelAction;
import com.base.frame.service.mq.MqService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;


@Slf4j
@Component
public class OtherConsumer {

    @Autowired
    private MqService mqService;

    @RabbitListener(queues = ConstOtherMQ.OTHER_QUEQUE, containerFactory = ConstOtherMQ.OTHER_LISTENER_CONTAINER_FACTORY)
    public void consume(@Header("amqp_receivedRoutingKey") String routeKey, Message message, Channel channel) throws IOException {
        String data = new String(message.getBody(), Charset.forName("utf-8"));
        if (data != null) {
            String action = ConstChannelAction.REJECT;
            BaseResult apiResult = ResultGenerator.fail("默认失败");
            try {
                apiResult = mqService.consumeOtherQueue(data);
                if (apiResult.isSuccess()) {
                    action = ConstChannelAction.ACCEPT;
                }
            } catch (Exception e) {
                apiResult.setMessage(e.getMessage());
            } finally {
                if (action.equals(ConstChannelAction.ACCEPT)) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } else if (action.equals(ConstChannelAction.REJECT)) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                }
            }

        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
