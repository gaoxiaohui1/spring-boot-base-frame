package com.base.frame.event.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BizService {
    @Autowired
    EventPublishService eventPublishService;

    public void orderCreate(String orderNumber) {
        log.info("BizService.orderCreate()->start->" + orderNumber);
        log.info("create->start->" + orderNumber);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("create->end->" + orderNumber);
        log.info("create-success-event-publish->start->" + orderNumber);
        eventPublishService.orderCreateSuccess(orderNumber);
        log.info("create-success-event-publish->end->" + orderNumber);
        log.info("BizService.orderCreate()->end->" + orderNumber);
    }
}
