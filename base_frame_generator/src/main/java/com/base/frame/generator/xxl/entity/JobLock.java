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
    @TableName("xxl_job_lock")
    @ApiModel(value="JobLock对象", description="")
    public class JobLock implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "锁名称")
    private String lockName;


}
