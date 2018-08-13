package com.base.frame.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 订单创建输入参数
 */
@Data
public class OrderCreateInput {

    /**
     * 产品信息
     */
    @Valid
    @NotNull(message = "产品信息不能为空")
    @ApiModelProperty(value = "产品信息", required = true)
    private ProductInfoDto productInfoDto;


    /**
     * 产品使用人信息
     */
    @Valid
    @NotNull(message = "产品使用人信息不能为空")
    @Size(min = 1, message = "至少有一个产品使用人信息")
    @ApiModelProperty(value = "产品使用人信息", required = true)
    private List<UserInfoDto> userInfoDtoList;
}
