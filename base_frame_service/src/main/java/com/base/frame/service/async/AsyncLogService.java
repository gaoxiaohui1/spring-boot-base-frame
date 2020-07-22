package com.base.frame.service.async;

import com.base.frame.model.base.BaseResult;
import com.base.frame.service.common.BaseService;
import com.base.frame.service.config.AsyncConfig;
import com.base.frame.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


@Service
@Async(AsyncConfig.LOG_ASYNC_EXECUTOR)
public class AsyncLogService extends BaseService {
    @Autowired
    LogService logService;

    public void logAsyncVoid() {
        logger.info(System.currentTimeMillis() + "    log start void async");
        logService.logAsyncVoid();
        logger.info(System.currentTimeMillis() + "    log end void async");
    }

    public Future<BaseResult> logAsync() {
        return AsyncResult.forValue(logService.logAsync());
    }
}
