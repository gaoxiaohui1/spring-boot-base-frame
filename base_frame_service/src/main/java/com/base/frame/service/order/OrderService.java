package com.base.frame.service.order;

import com.base.frame.aop.anation.OrderReadDbAnation;
import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.dao.OrderDao;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.model.context.UserInfoContextHolder;
import com.base.frame.model.dto.input.OrderCreateInput;
import com.base.frame.model.entity.OrderEntity;
import com.base.frame.service.common.BaseService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@Service
public class OrderService extends BaseService {
    @Autowired
    private OrderDao orderDao;

    /**
     * sqlserver读数据
     *
     * @param id
     * @return
     */
    @OrderReadDbAnation
    public BaseResult<OrderEntity> getOrderByID(@Max(value = 100, message = "ID最大为100") Long id) {
        logger.error("用户上下文信息：" + ToolJson.modelToJson(UserInfoContextHolder.getUser()));
        OrderEntity orderEntity = orderDao.getOrderByID(id);
        assertNotNull(orderEntity, "不存在对应订单信息", true);
        return ResultGenerator.success(orderEntity);
    }

    public BaseResult<OrderCreateInput> createOrder(@Valid OrderCreateInput orderCreateInput) {
        BaseResult<OrderCreateInput> result = ResultGenerator.fail(orderCreateInput, "异常");
        assertResult(result, "创建订单异常", false);
        return result;
    }
}
