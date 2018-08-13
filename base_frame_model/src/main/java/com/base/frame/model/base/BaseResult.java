package com.base.frame.model.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础输出
 */
@Data
@ApiModel(value = "BaseResult")
public class BaseResult<T> implements Serializable {
    /**
     * 1-成功；其他失败
     */
    @ApiModelProperty(value = "1-成功；其他失败")
    private Integer code;

    /**
     * 具体错误码
     */
    @ApiModelProperty(value = "具体错误码")
    private Integer errorCode;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "1-成功；其他失败")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "1-成功；其他失败")
    private T data;
}
