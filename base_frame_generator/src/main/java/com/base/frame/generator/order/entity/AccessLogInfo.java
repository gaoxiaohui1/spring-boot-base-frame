package com.base.frame.generator.order.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableName("Order_AccessLogInfo")
    @ApiModel(value="AccessLogInfo对象", description="")
    public class AccessLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableField("Id")
    private Long Id;

        @TableField("Url")
    private String Url;

        @TableField("Params")
    private String Params;

        @TableField("Data")
    private String Data;

        @TableField("ResultCode")
    private String ResultCode;

        @TableField("ErrorInfo")
    private String ErrorInfo;

        @TableField("Name")
    private String Name;

        @TableField("CreateTime")
    private LocalDateTime CreateTime;

        @TableField("TraceID")
    private String TraceID;

        @TableField("UseMillisecond")
    private Long UseMillisecond;

        @TableField("Header")
    private String Header;

        @TableField("ReferUrl")
    private String ReferUrl;


}
