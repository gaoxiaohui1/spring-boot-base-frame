package com.base.frame.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户信息
 */
@Data
public class UserInfoDto {

    /**
     * 服务开始时间
     */
    @NotNull(message = "服务开始时间不能为空")
    @NotBlank(message = "服务开始时间不能为空")
    @Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$", message = "服务开始时间格式错误")
    @ApiModelProperty(value = "服务开始时间(yyyy-MM-dd)")
    private String serviceDateStart;

    /**
     * 服务结束时间
     */
    @NotNull(message = "服务结束时间不能为空")
    @NotBlank(message = "服务结束时间不能为空")
    @Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$", message = "服务结束时间格式错误")
    @ApiModelProperty(value = "服务结束时间(yyyy-MM-dd)")
    private String serviceDateEnd;

    /**
     * 使用人ID
     */
    @NotNull(message = "使用人ID不能为空")
    @Min(value = 1, message = "使用人ID不能小于1")
    @ApiModelProperty(value = "使用人ID", required = true)
    private Long userID;
}
