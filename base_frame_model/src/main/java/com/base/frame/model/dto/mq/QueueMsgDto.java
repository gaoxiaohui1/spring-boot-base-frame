package com.base.frame.model.dto.mq;

import com.base.frame.model.base.MqBaseDto;
import lombok.Data;

@Data
public class QueueMsgDto extends MqBaseDto {
    private Long orderID;
    private String orderNumber;
}
