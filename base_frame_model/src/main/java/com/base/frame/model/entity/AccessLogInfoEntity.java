package com.base.frame.model.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 外部调用当前系统
 */
@Data
@TableName("AccessLogInfo")
public class AccessLogInfoEntity {
    /**
     * 主键
     */
    @TableId(value="ID", type= IdType.AUTO)
    private Long id;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 传递参数
     */
    private String params;
    /**
     * 返回结果
     */
    private String data;
    /**
     * 返回code
     */
    private String resultCode;
    /**
     * 错误信息
     */
    private String errorInfo;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 调用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS", timezone="GMT+8")
    private Date createTime;
    /**
     * 寻踪id
     */
    private String traceID;
    /**
     * 响应时间
     */
    private Long useMillisecond;
    /**
     * header信息
     */
    private String header;
    /**
     * 链接url
     */
    private String referUrl;
}
