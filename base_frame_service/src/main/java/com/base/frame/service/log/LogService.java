package com.base.frame.service.log;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.service.common.BaseService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LogService extends BaseService {

    public void logAsyncVoid() {
        logger.info(System.currentTimeMillis() + "    log start void");
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(System.currentTimeMillis() + "    log end void");
    }

    public BaseResult logAsync() {
        logger.info(System.currentTimeMillis() + "    log start res");
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(System.currentTimeMillis() + "    log end res");
        return ResultGenerator.success();
    }
}
