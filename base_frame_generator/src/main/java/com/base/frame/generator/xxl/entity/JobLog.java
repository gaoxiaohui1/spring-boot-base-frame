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
@TableName("xxl_job_log")
@ApiModel(value = "JobLog对象", description = "")
public class JobLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "执行器主键ID")
    private Integer jobGroup;

    @ApiModelProperty(value = "任务，主键ID")
    private Integer jobId;

    @ApiModelProperty(value = "执行器地址，本次执行的地址")
    private String executorAddress;

    @ApiModelProperty(value = "执行器任务handler")
    private String executorHandler;

    @ApiModelProperty(value = "执行器任务参数")
    private String executorParam;

    @ApiModelProperty(value = "执行器任务分片参数，格式如 1/2")
    private String executorShardingParam;

    @ApiModelProperty(value = "失败重试次数")
    private Integer executorFailRetryCount;

    @ApiModelProperty(value = "调度-时间")
    private LocalDateTime triggerTime;

    @ApiModelProperty(value = "调度-结果")
    private Integer triggerCode;

    @ApiModelProperty(value = "调度-日志")
    private String triggerMsg;

    @ApiModelProperty(value = "执行-时间")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "执行-状态")
    private Integer handleCode;

    @ApiModelProperty(value = "执行-日志")
    private String handleMsg;

    @ApiModelProperty(value = "告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败")
    private Integer alarmStatus;


}
