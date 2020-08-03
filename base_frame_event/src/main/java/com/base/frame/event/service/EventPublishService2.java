//package com.base.frame.event.service;
//
//
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class EventPublishService2 implements ApplicationEventPublisherAware {
//    private ApplicationEventPublisher applicationEventPublisher;
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }
//
//    public void orderCreateSuccess(OrderCreateSuccessEvent event) {
//        log.info("applicationEventPublisher.publishEvent()->start->" + event.getData() + "->" + applicationEventPublisher.toString());
//        applicationEventPublisher.publishEvent(event);
//        log.info("applicationEventPublisher.publishEvent()->end->" + event.getData() + "->" + applicationEventPublisher.toString());
//    }
//}
