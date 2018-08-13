package com.base.frame.model.dto;

import com.base.frame.model.entity.UserEntity;
import lombok.Data;

/**
 * 用户数据处理
 */
@Data
public class UserDto extends UserEntity {
    /**
     * 创建时间
     */
    private String addTime;
}
