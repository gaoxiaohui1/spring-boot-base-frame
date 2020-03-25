package com.base.frame.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.base.frame.model.entity.OrderInfoEntity;
import com.base.frame.model.entity.base.OrderInfo;

import java.util.List;

public interface OrderInfoDao extends BaseMapper<OrderInfoEntity> {
    int insertBatch(List<OrderInfo> entityList);
}
