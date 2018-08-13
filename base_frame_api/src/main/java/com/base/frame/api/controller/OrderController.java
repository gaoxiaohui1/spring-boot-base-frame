package com.base.frame.api.controller;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.model.dto.input.OrderCreateInput;
import com.base.frame.model.entity.OrderEntity;
import com.base.frame.service.order.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 订单
 */
@RequestMapping("/api/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * sqlServer查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "sqlServer查询", notes = "sqlServer查询")
    @RequestMapping(path = "sqlServerSelect", method = RequestMethod.GET)
    public BaseResult<OrderEntity> getOrderByID(Long id) {
        BaseResult<OrderEntity> result = orderService.getOrderByID(id);
        return result;
    }

    /**
     * 复杂参数校验
     *
     * @param orderCreateInput
     * @return
     */
    @ApiOperation(value = "复杂参数校验", notes = "复杂参数校验")
    @RequestMapping(path = "validateParam", method = RequestMethod.POST)
    public BaseResult<OrderCreateInput> validateParam(@RequestBody OrderCreateInput orderCreateInput) {
        BaseResult<OrderCreateInput> result = orderService.createOrder(orderCreateInput);
        return result;
    }
}
