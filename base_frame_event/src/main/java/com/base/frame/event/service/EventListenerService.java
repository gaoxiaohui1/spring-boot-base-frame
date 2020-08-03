package com.base.frame.event.service;

import com.base.frame.event.config.EventAsyncConfig;
import com.base.frame.model.dto.event.OrderCreateEvent;
import com.base.frame.model.dto.event.OrderCreateSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class EventListenerService {

    @EventListener
    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
    public void orderCreate(OrderCreateSuccessEvent event) {
        log.info("AsyncEventListener->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("AsyncEventListener->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("AsyncEventListener->end->" + event.getData());
    }

    @EventListener
    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
    public void orderCreate1(OrderCreateSuccessEvent event) {
        log.info("AsyncEventListener1->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("AsyncEventListener1->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("AsyncEventListener1->end->" + event.getData());
    }

    @EventListener
    public void orderCreate2(OrderCreateSuccessEvent event) {
        log.info("EventListener->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("EventListener->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("EventListener->end->" + event.getData());
    }

    @EventListener
    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
    public void create(OrderCreateEvent event){
        log.info("listener-create->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("listener-create->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("listener-create->end->" + event.getData());
    }

    @EventListener(condition = "#event.success")
    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
    public void createSuccess(OrderCreateEvent event){
        log.info("listener-create-success->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("listener-create-success->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("listener-create-success->end->" + event.getData());
    }

    @EventListener(condition = "!{#event.success}")
    @Async(EventAsyncConfig.EVENT_ASYNC_EXECUTOR)
    public void createFail(OrderCreateEvent event){
        log.info("listener-create-fail->start->" + event.getData());
        try {
            Integer sleepTime = new Random().nextInt(10);
            log.info("listener-create-fail->start->" + sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("listener-create-fail->end->" + event.getData());
    }
}
