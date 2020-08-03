//package com.base.frame.event.service;
//
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class EventListenerService1 {
//    @EventListener
//    public void orderCreate(OrderCreateSuccessEvent event) {
//        log.info("EventListener->start->" + event.getData());
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("EventListener->end->" + event.getData());
//    }
//}
