package com.base.frame.service.mq;

import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.common.tools.time.ToolDate;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.model.dto.mq.QueueMsgDto;
import com.base.frame.rabbitmq.own.sender.DelayMQueueSender;
import com.base.frame.rabbitmq.own.sender.DelayQQueueSender;
import com.base.frame.rabbitmq.own.sender.DirectQueueSender;
import com.base.frame.rabbitmq.own.sender.FanoutSender;
import com.base.frame.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MqService extends BaseService {
    @Autowired
    private DirectQueueSender directQueueSender;
    @Autowired
    private FanoutSender fanoutSender;
    @Autowired
    private DelayMQueueSender delayMQueueSender;
    @Autowired
    private DelayQQueueSender delayQQueueSender;

    public BaseResult sendMsgToDirectQueue(QueueMsgDto dto) {
        directQueueSender.send(dto);
        return ResultGenerator.success();
    }

    public BaseResult sendMsgToFanout(QueueMsgDto dto) {
        fanoutSender.send(dto);
        return ResultGenerator.success();
    }

    public BaseResult sendDelayMsg(QueueMsgDto dto) {
        delayMQueueSender.send(dto);
        return ResultGenerator.success();
    }

    public BaseResult sendMsgToDelayQueue(QueueMsgDto dto) {
        delayQQueueSender.send(dto);
        return ResultGenerator.success();
    }

    public BaseResult consumeDirectQueue(QueueMsgDto dto) {
        System.out.println(ToolDate.formatTimesTampDate(new Date()).concat("    消费DirectQueue信息：").concat(ToolJson.modelToJson(dto)));
        return ResultGenerator.success();
    }

    public BaseResult consumeFanoutQueue(QueueMsgDto dto) {
        System.out.println(ToolDate.formatTimesTampDate(new Date()).concat("    消费FanoutQueue信息：").concat(ToolJson.modelToJson(dto)));
        return ResultGenerator.success();
    }

    public BaseResult consumeOtherQueue(String msg) {
        System.out.println(ToolDate.formatTimesTampDate(new Date()).concat("    消费OtherQueue信息：").concat(msg));
        return ResultGenerator.success();
    }

    public BaseResult consumeDelayExecuteQueue(QueueMsgDto dto) {
        System.out.println(ToolDate.formatTimesTampDate(new Date()).concat("    消费DelayExecuteQueue信息：").concat(ToolJson.modelToJson(dto)));
        return ResultGenerator.success();
    }
}
