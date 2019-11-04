package com.base.frame.service.user;

import com.base.frame.aop.anation.DbAnation;
import com.base.frame.aop.anation.UserWriteDbAnation;
import com.base.frame.dao.UserDao;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.model.dto.UserDto;
import com.base.frame.model.entity.UserEntity;
import com.base.frame.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Service
public class UserService extends BaseService {
    @Autowired
    private UserDao userDao;

    /**
     * mysql写库查询
     *
     * @param id
     * @return
     */
    @DbAnation(dbType = "user_write")
    public BaseResult<UserDto> getUserByID(@Min(value = 0, message = "用户ID必须大于0") Long id) {
        UserDto userDto = userDao.getUserByID(id);
        assertNotNull(userDto, "不存在对应信息", true);
        return ResultGenerator.success(userDto);
    }

    /**
     * mysql读库查询
     *
     * @param name
     * @return
     */
    @DbAnation(dbType = "user_read")
    public BaseResult<UserDto> getUserByName(@NotNull(message = "用户名不能为空") String name) {
        UserDto userDto = userDao.getUserByName(name);
        assertNotNull(userDto, "不存在对应信息", true);
        return ResultGenerator.success(userDto);
    }

    /**
     * mysql写库插入
     *
     * @param name
     * @return
     */
    @UserWriteDbAnation
    public BaseResult<Integer> insertUserWrite(@NotBlank(message = "用户名不能为空") String name) {
        return inserUserEntity(name);
    }

    /**
     * mysql读库插入
     *
     * @param name
     * @return
     */
    @DbAnation(dbType = "user_read")
    public BaseResult<Integer> insertUserRead(@NotEmpty(message = "用户名不能为空") String name) {
        return inserUserEntity(name);
    }


    private BaseResult<Integer> inserUserEntity(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(name);
        Integer effortRow = userDao.insert(userEntity);
        assertEqual(effortRow, 1, "插入异常", false);
        return ResultGenerator.success(new Integer(userEntity.getId().intValue()));
    }
}
