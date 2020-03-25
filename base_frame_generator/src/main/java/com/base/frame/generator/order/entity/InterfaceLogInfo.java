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
    @TableName("Order_InterfaceLogInfo")
    @ApiModel(value="InterfaceLogInfo对象", description="")
    public class InterfaceLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    private String params;

    private String data;

        @TableField("resultCode")
    private String resultCode;

        @TableField("errorInfo")
    private String errorInfo;

    private String name;

        @TableField("createTime")
    private LocalDateTime createTime;

        @TableField("traceId")
    private String traceId;

        @TableField("useMillisecond")
    private Long useMillisecond;

    private String header;

        @TableField("orderID")
    private Long orderID;

        @TableField("orderDetailID")
    private Long orderDetailID;


}
