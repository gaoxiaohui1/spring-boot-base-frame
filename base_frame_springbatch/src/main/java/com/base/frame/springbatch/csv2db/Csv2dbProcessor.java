package com.base.frame.springbatch.csv2db;

import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.model.entity.base.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class Csv2dbProcessor implements ItemProcessor<OrderInfo, OrderInfo> {

    @Override
    public OrderInfo process(OrderInfo orderInfo) throws Exception {
        OrderInfo processedOrderInfo = new OrderInfo();
        processedOrderInfo.setSubOrderNumber("1_".concat(orderInfo.getSubOrderNumber()));
        processedOrderInfo.setOrderNumber("1_".concat(orderInfo.getOrderNumber()));
        processedOrderInfo.setHistoryNumber("1_".concat(orderInfo.getHistoryNumber()));
        processedOrderInfo.setStartDate("1_".concat(orderInfo.getStartDate()));
        processedOrderInfo.setEndDate("1_".concat(orderInfo.getEndDate()));
        processedOrderInfo.setOrderMoney("1_".concat(orderInfo.getOrderMoney()));
        processedOrderInfo.setOrderRealMoney("1_".concat(orderInfo.getOrderRealMoney()));
        processedOrderInfo.setProductID("1_".concat(orderInfo.getProductID()));
        processedOrderInfo.setProductName("1_".concat(orderInfo.getProductName()));
        processedOrderInfo.setOrderSource("1_".concat(orderInfo.getOrderSource()));
        log.info("------------------------------------------convert success------------------------------------------");
        return processedOrderInfo;
    }
}
