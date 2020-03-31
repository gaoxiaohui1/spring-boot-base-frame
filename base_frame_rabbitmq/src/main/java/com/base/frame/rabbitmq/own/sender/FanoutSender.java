package com.base.frame.rabbitmq.own.sender;

import com.base.frame.common.tools.time.ToolDate;
import com.base.frame.model.base.MqBaseDto;
import com.base.frame.model.dto.mq.QueueMsgDto;
import com.base.frame.rabbitmq.own.constant.ConstExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class FanoutSender {
    @Autowired
    private DefaultFanoutSender defaultFanoutSender;

    public void send(QueueMsgDto dto) {
        dto.setExChange(ConstExchange.FANOUT_DEFAULT_EXCHANGE);
        dto.setQueue("");
        dto.setUuid(UUID.randomUUID().toString());
        dto.setQT(ToolDate.formatTimesTampDate(new Date()));
        defaultFanoutSender.send(dto);
    }
}
