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
    @TableName("xxl_job_group")
    @ApiModel(value="JobGroup对象", description="")
    public class JobGroup implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "执行器AppName")
    private String appName;

            @ApiModelProperty(value = "执行器名称")
    private String title;

            @ApiModelProperty(value = "排序")
    private Integer order;

            @ApiModelProperty(value = "执行器地址类型：0=自动注册、1=手动录入")
    private Integer addressType;

            @ApiModelProperty(value = "执行器地址列表，多地址逗号分隔")
    private String addressList;


}
