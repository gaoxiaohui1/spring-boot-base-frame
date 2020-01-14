package com.base.frame.service.log;

import com.base.frame.aop.IRecordAccessLog;
import com.base.frame.aop.anation.DbAnation;
import com.base.frame.dao.AccessLogInfoDao;
import com.base.frame.model.entity.AccessLogInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
public class AccessLogService implements IRecordAccessLog {

    @Autowired
    private AccessLogInfoDao accessLogInfoDao;

    @Async
    @Override
    @DbAnation(dbType = "user_write")
    public void save(String url, String headers, String params
            , String paramType, String resultCode, String errorInfo, String data
            , Long objId, String referUrl, Long executeTime, Date operateTime) {
        AccessLogInfoEntity info = new AccessLogInfoEntity();
        info.setName(paramType);
        info.setCreateTime(operateTime);
        info.setUrl(url);
        info.setData(data);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        info.setTraceID(uuid);
        info.setHeader(headers);
        info.setParams(params);
        info.setResultCode(resultCode);
        info.setErrorInfo(errorInfo);
        info.setReferUrl(referUrl);
        info.setUseMillisecond(executeTime);
        accessLogInfoDao.insert(info);
    }

}
