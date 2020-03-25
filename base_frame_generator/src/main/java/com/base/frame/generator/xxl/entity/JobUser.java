package com.base.frame.generator.xxl.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author G
* @since 2020-03-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("xxl_job_user")
    @ApiModel(value="JobUser对象", description="")
    public class JobUser implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "账号")
    private String username;

            @ApiModelProperty(value = "密码")
    private String password;

            @ApiModelProperty(value = "角色：0-普通用户、1-管理员")
    private Integer role;

            @ApiModelProperty(value = "权限：执行器ID列表，多个逗号分割")
    private String permission;


}
