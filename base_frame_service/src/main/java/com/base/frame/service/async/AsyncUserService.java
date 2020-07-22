package com.base.frame.service.async;

import com.base.frame.model.base.BaseResult;
import com.base.frame.service.common.BaseService;
import com.base.frame.service.config.AsyncConfig;
import com.base.frame.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Async(AsyncConfig.USER_ASYNC_EXECUTOR)
public class AsyncUserService extends BaseService {
    @Autowired
    UserService userService;

    public void userAsyncVoid() {
        logger.info(System.currentTimeMillis() + "    user start void async");
        userService.userAsyncVoid();
        logger.info(System.currentTimeMillis() + "    user end void async");
    }

    public Future<BaseResult> userAsync() {
        return AsyncResult.forValue(userService.userAsync());
    }
}
