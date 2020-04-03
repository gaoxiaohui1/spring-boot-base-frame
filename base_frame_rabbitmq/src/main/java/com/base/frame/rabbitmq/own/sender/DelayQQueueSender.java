package com.base.frame.rabbitmq.own.sender;

import com.base.frame.common.tools.time.ToolDate;
import com.base.frame.model.dto.mq.QueueMsgDto;
import com.base.frame.rabbitmq.own.constant.ConstExchange;
import com.base.frame.rabbitmq.own.constant.ConstQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 队列延时发送
 */
@Component
public class DelayQQueueSender {
    @Autowired
    private DefaultDirectSender defaultDirectSender;

    public void send(QueueMsgDto dto) {
        dto.setExChange(ConstExchange.DIRECT_DEFAULT_EXCHANGE);
        dto.setQueue(ConstQueue.DIRECT_DELAY_Q_QUEQUE);
        dto.setUuid(UUID.randomUUID().toString());
        dto.setQT(ToolDate.formatTimesTampDate(new Date()));
        defaultDirectSender.send(dto);
    }
}
