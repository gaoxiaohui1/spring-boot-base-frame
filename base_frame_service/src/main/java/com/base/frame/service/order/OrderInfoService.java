package com.base.frame.service.order;

import com.base.frame.aop.anation.DbAnation;
import com.base.frame.dao.OrderInfoDao;
import com.base.frame.model.entity.base.OrderInfo;
import com.base.frame.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoService extends BaseService {
    @Autowired
    private OrderInfoDao orderInfoDao;

    /**
     *
     * @param entityList
     * @return
     */
    @DbAnation(dbType = "user_write")
    public int insertBatch(List<OrderInfo> entityList) {
        return orderInfoDao.insertBatch(entityList);
    }
}
