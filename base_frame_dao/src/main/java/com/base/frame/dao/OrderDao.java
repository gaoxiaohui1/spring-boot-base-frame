package com.base.frame.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.base.frame.model.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 */
public interface OrderDao extends BaseMapper<OrderEntity> {
    /**
     *
     * @param id
     * @return
     */
     OrderEntity getOrderByID(@Param(value = "id") Long id);

    /**
     *
     * @param number
     * @return
     */
    @Select("select id,orderID,GETDATE() AS addTime from OrderInfo WITH(NOLOCK) where orderID= #{number}")
    OrderEntity getOrderByNumber(@Param(value = "number") String number);
}
