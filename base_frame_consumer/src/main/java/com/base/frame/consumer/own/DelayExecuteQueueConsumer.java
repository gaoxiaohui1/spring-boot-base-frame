package com.base.frame.consumer.own;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.dto.mq.QueueMsgDto;
import com.base.frame.rabbitmq.own.constant.ConstQueue;
import com.base.frame.rabbitmq.own.consumer.DefaultFanoutConsumer;
import com.base.frame.service.mq.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = ConstQueue.DELAY_EXECUTE_QUEQUE)
public class DelayExecuteQueueConsumer extends DefaultFanoutConsumer<QueueMsgDto> {

    @Autowired
    private MqService mqService;

    @Override
    public BaseResult process(QueueMsgDto dto) {
        return mqService.consumeDelayExecuteQueue(dto);
    }
}