//package com.base.frame.event.service;
//
//import com.base.frame.event.config.EventAsyncConfig;
//import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class EventListenerService2 {
//
//    @EventListener
//    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
//    public void orderCreate(OrderCreateSuccessEvent event) {
//        log.info("AsyncEventListener->start->" + event.getData());
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("AsyncEventListener->end->" + event.getData());
//    }
//}
