package com.base.frame.generator.xxl.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.time.LocalDateTime;
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
    @TableName("xxl_job_log_report")
    @ApiModel(value="JobLogReport对象", description="")
    public class JobLogReport implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "调度-时间")
    private LocalDateTime triggerDay;

            @ApiModelProperty(value = "运行中-日志数量")
    private Integer runningCount;

            @ApiModelProperty(value = "执行成功-日志数量")
    private Integer sucCount;

            @ApiModelProperty(value = "执行失败-日志数量")
    private Integer failCount;


}
