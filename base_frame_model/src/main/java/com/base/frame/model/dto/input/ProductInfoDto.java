package com.base.frame.model.dto.input;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 产品信息
 */
@Data
public class ProductInfoDto {

    /**
     * 产品ID
     */
    @NotNull(message = "产品ID不能为空")
    @Min(value = 1, message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID", required = true)
    private Integer productID;

    /**
     * 产品名称
     */
    @NotNull(message = "产品名称不能为空")
    @NotBlank(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称", required = true)
    private String productName;

}
