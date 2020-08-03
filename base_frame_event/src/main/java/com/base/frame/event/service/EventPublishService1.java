//package com.base.frame.event.service;
//
//
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class EventPublishService1 {
//    @Autowired
//    ApplicationEventPublisher applicationEventPublisher;
//
//    public void orderCreateSuccess(OrderCreateSuccessEvent event) {
//        log.info("applicationEventPublisher.publishEvent()->start->" + event.getData() + "->" + applicationEventPublisher.toString());
//        applicationEventPublisher.publishEvent(event);
//        log.info("applicationEventPublisher.publishEvent()->end->" + event.getData() + "->" + applicationEventPublisher.toString());
//    }
//}
