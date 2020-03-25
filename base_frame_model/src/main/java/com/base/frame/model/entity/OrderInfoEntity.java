package com.base.frame.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.base.frame.model.entity.base.OrderInfo;
import lombok.Data;

@Data
@TableName("order_info")
public class OrderInfoEntity extends OrderInfo {
    /**
     * 自增主键ID
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;
}
