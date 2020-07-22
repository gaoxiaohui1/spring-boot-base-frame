package com.base.frame.service.async;

import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncService extends BaseService {
    @Autowired
    AsyncLogService logService;
    @Autowired
    AsyncUserService userService;
    @Autowired
    AsyncOrderService orderService;

    public BaseResult async() {
        logger.info(System.currentTimeMillis() + "    async start");
        Future<BaseResult> logFuture = logService.logAsync();
        Future<BaseResult> orderFuture = orderService.orderAsync();
        Future<BaseResult> userFuture = userService.userAsync();
        logger.info(System.currentTimeMillis() + "    async start get");
        try {
            logger.info(System.currentTimeMillis() + "    async start get log");
            logger.info("logFuture->" + ToolJson.modelToJson(logFuture.get()));
            logger.info(System.currentTimeMillis() + "    async end get log");
            logger.info(System.currentTimeMillis() + "    async start get order");
            logger.info("orderFuture->" + ToolJson.modelToJson(orderFuture.get()));
            logger.info(System.currentTimeMillis() + "    async end get order");
            logger.info(System.currentTimeMillis() + "    async start get user");
            logger.info("userFuture->" + ToolJson.modelToJson(userFuture.get()));
            logger.info(System.currentTimeMillis() + "    async end get user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(System.currentTimeMillis() + "    async end get");
        logger.info(System.currentTimeMillis() + "    async end");
        return ResultGenerator.success();
    }

    public BaseResult asyncVoid() {
        logger.info(System.currentTimeMillis() + "    asyncVoid start");
        logService.logAsyncVoid();
        userService.userAsyncVoid();
        orderService.orderAsyncVoid();
        logger.info(System.currentTimeMillis() + "    asyncVoid end");
        return ResultGenerator.success();
    }

    public BaseResult asyncMix() {
        logger.info(System.currentTimeMillis() + "    asyncMix start");
        Future<BaseResult> logFuture = logService.logAsync();
        Future<BaseResult> orderFuture = orderService.orderAsync();
        Future<BaseResult> userFuture = userService.userAsync();
        logService.logAsyncVoid();
        userService.userAsyncVoid();
        orderService.orderAsyncVoid();
        logger.info(System.currentTimeMillis() + "    asyncMix end");
        return ResultGenerator.success();
    }
}
