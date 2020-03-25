package com.base.frame.springbatch.csv2db;

import com.base.frame.model.entity.base.OrderInfo;
import com.base.frame.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Csv2dbWriter implements ItemWriter<OrderInfo> {
    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public void write(List<? extends OrderInfo> list) throws Exception {
        log.info("数据开始入库 count = ", list.size());
        List<OrderInfo> entityList = new ArrayList<>();
        list.forEach(x -> {
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(x, orderInfo);
            entityList.add(orderInfo);
        });
        orderInfoService.insertBatch(entityList);
        log.info("数据结束入库 count = ", list.size());
    }
}
