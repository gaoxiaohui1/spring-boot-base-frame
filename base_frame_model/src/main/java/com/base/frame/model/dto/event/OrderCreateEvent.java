package com.base.frame.model.dto.event;

public class OrderCreateEvent extends GenericEvent<String>{
    public OrderCreateEvent(String data, Boolean success) {
        super(data, success);
    }
}
