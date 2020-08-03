package com.base.frame.event.service;

import com.base.frame.model.dto.event.OrderCreateEvent;
import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventPublishService {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void orderCreateSuccess(String orderNumber) {
        log.info("EventPublishService.orderCreateSuccess()->start->" + orderNumber);
        OrderCreateSuccessEvent event = new OrderCreateSuccessEvent(this, orderNumber);
        applicationEventPublisher.publishEvent(event);
        log.info("EventPublishService.orderCreateSuccess()->end->" + orderNumber);
    }

    public void orderCreate(String orderNumber, Boolean success) {
        log.info("EventPublishService.orderCreate()->start->" + orderNumber);
        OrderCreateEvent event = new OrderCreateEvent(orderNumber, success);
        applicationEventPublisher.publishEvent(event);
        log.info("EventPublishService.orderCreate()->end->" + orderNumber);
    }
}
