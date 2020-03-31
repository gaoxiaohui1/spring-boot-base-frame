package com.base.frame.model.base;

import lombok.Data;

@Data
public class MqBaseDto {
    private String exChange;
    private String queue;
    private String uuid;
    private String qT;
}
