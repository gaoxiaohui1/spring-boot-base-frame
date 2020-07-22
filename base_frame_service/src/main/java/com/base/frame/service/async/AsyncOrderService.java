package com.base.frame.service.async;

import com.base.frame.model.base.BaseResult;
import com.base.frame.service.common.BaseService;
import com.base.frame.service.config.AsyncConfig;
import com.base.frame.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Async(AsyncConfig.ORDER_ASYNC_EXECUTOR)
public class AsyncOrderService extends BaseService {
    @Autowired
    OrderService orderService;

    public void orderAsyncVoid() {
        logger.info(System.currentTimeMillis() + "    order start void async");
        orderService.orderAsyncVoid();
        logger.info(System.currentTimeMillis() + "    order end void async");
    }

    public Future<BaseResult> orderAsync() {
        return AsyncResult.forValue(orderService.orderAsync());
    }
}
