package com.base.frame.api.controller;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.dto.mq.QueueMsgDto;
import com.base.frame.service.mq.MqService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mq")
public class MqController {

    @Autowired
    private MqService mqService;

    /**
     * DirectExchange发送消息到队列
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "DirectExchange发送消息到队列", notes = "DirectExchange发送消息到队列")
    @RequestMapping(path = "sendMsgToDirectQueue", method = RequestMethod.GET)
    public BaseResult sendMsgToDirectQueue(QueueMsgDto dto) {
        BaseResult result = mqService.sendMsgToDirectQueue(dto);
        return result;
    }

    /**
     * FanoutExchange发送消息
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "FanoutExchange发送消息", notes = "FanoutExchange发送消息")
    @RequestMapping(path = "sendMsgToFanout", method = RequestMethod.GET)
    public BaseResult sendMsgToFanout(QueueMsgDto dto) {
        BaseResult result = mqService.sendMsgToFanout(dto);
        return result;
    }

    /**
     * 发送延时消息
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "发送延时消息", notes = "发送延时消息")
    @RequestMapping(path = "sendDelayMsg", method = RequestMethod.GET)
    public BaseResult sendDelayMsg(QueueMsgDto dto) {
        BaseResult result = mqService.sendDelayMsg(dto);
        return result;
    }

    /**
     * 发送消息到延时队列
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "发送消息到延时队列", notes = "发送消息到延时队列")
    @RequestMapping(path = "sendMsgToDelayQueue", method = RequestMethod.GET)
    public BaseResult sendMsgToDelayQueue(QueueMsgDto dto) {
        BaseResult result = mqService.sendMsgToDelayQueue(dto);
        return result;
    }
}
