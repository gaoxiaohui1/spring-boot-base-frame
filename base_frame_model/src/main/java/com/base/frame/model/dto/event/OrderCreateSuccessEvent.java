package com.base.frame.model.dto.event;


import org.springframework.context.ApplicationEvent;


public class OrderCreateSuccessEvent extends ApplicationEvent {
    private String data;

    public OrderCreateSuccessEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
