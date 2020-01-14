package com.base.frame.aop;

import java.util.Date;

/**
 * 声明一个接口，解耦对dao层的操作
 */
public interface IRecordAccessLog {
    void save(String url, String headers, String params, String paramType, String resultCode, String errorInfo, String data, Long objId,
              String referUrl, Long executeTime, Date operateTime);
}
