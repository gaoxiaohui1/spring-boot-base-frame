//package com.base.frame.event.service;
//
//
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class EventPublishService0 {
//    @Autowired
//    ApplicationContext applicationContext;
//
//    public void orderCreateSuccess(OrderCreateSuccessEvent event) {
//        log.info("applicationContext.publishEvent()->start->" + event.getData() + "->" + applicationContext.toString());
//        applicationContext.publishEvent(event);
//        log.info("applicationContext.publishEvent()->end->" + event.getData() + "->" + applicationContext.toString());
//    }
//}
