package com.base.frame.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 用户表
 */
@Data
@TableName("UserInfo")
public class UserEntity {
    /**
     * 自增主键ID
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名称
     */
    private String userName;
}