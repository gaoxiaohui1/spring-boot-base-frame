package com.base.frame.api.controller;

import com.base.frame.event.service.BizService;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/event")
public class EventController {
    @Autowired
    BizService bizService;

    @RequestMapping(path = "ordercreate", method = RequestMethod.GET)
    public BaseResult orderCreate(String orderNumber) {
        bizService.orderCreate(orderNumber);
        return ResultGenerator.success();
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public BaseResult create(String orderNumber, Integer type) {
        bizService.orderCreate(orderNumber, type);
        return ResultGenerator.success();
    }
}
