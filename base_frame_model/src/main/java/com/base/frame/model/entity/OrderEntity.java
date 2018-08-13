package com.base.frame.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName("OrderInfo")
public class OrderEntity {
    /**
     * 自增主键ID
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    private String orderID;
}
