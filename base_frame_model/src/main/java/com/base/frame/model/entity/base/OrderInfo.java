package com.base.frame.model.entity.base;

import lombok.Data;

@Data
public class OrderInfo {
    private String subOrderNumber;
    private String orderNumber;
    private String historyNumber;
    private String startDate;
    private String endDate;
    private String orderMoney;
    private String productID;
    private String productName;
    private String orderRealMoney;
    private String orderSource;
    public OrderInfo() {
    }
}
