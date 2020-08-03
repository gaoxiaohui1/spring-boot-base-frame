package com.base.frame.api.controller;

import com.base.frame.event.service.BizService;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/event")
public class EventController {
    @Autowired
    BizService bizService;

    @ApiOperation(value = "异步无返回", notes = "异步无返回")
    @RequestMapping(path = "ordercreate", method = RequestMethod.GET)
    public BaseResult orderCreate(String orderNumber) {
        bizService.orderCreate(orderNumber);
        return ResultGenerator.success();
    }
}
