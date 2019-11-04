package com.base.frame.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.base.frame.model.dto.UserDto;
import com.base.frame.model.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 */
public interface UserDao extends BaseMapper<UserEntity> {
    /**
     * @param id
     * @return
     */
    UserDto getUserByID(@Param(value = "id") Long id);

    /**
     * @param name
     * @return
     */
    @Select("select id,userName,now() AS addTime from UserInfo where userName= #{name} limit 1")
    UserDto getUserByName(@Param(value = "name") String name);
}
