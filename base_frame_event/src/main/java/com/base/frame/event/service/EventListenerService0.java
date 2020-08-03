//package com.base.frame.event.service;
//
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class EventListenerService0 implements ApplicationListener<OrderCreateSuccessEvent> {
//    @Override
//    public void onApplicationEvent(OrderCreateSuccessEvent event) {
//        log.info("ApplicationListener.onApplicationEvent()->start->" + event.getData());
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("ApplicationListener.onApplicationEvent()->end->" + event.getData());
//    }
//}
