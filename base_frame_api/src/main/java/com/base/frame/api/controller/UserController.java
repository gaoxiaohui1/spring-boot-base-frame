package com.base.frame.api.controller;

import com.base.frame.aop.anation.AccessLogAnation;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.dto.UserDto;
import com.base.frame.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 用户
 */
@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * mysql写库查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "mysql写库查询", notes = "mysql写库查询")
    @RequestMapping(path = "mySqlWriteSelect", method = RequestMethod.GET)
    public BaseResult<UserDto> mySqlWriteSelect(Long id) {
        BaseResult<UserDto> result = userService.getUserByID(id);
        return result;
    }

    /**
     * mysql读库查询
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "mysql读库查询", notes = "mysql读库查询")
    @RequestMapping(path = "mySqlReadSelect", method = RequestMethod.GET)
    public BaseResult<UserDto> mySqlReadSelect(String name) {
        BaseResult<UserDto> result = userService.getUserByName(name);
        return result;
    }

    /**
     * mysql写库插入
     *
     * @param name
     * @return
     */
    @AccessLogAnation
    @ApiOperation(value = "mysql写库插入", notes = "mysql写库插入")
    @RequestMapping(path = "mySqlWriteInsert", method = RequestMethod.GET)
    public BaseResult<Integer> mySqlWriteInsert(String name) {
        BaseResult<Integer> result = userService.insertUserWrite(name);
        return result;
    }

    /**
     * mysql读库插入
     *
     * @param name
     * @return
     */
    @AccessLogAnation
    @ApiOperation(value = "mysql读库插入", notes = "mysql读库插入")
    @RequestMapping(path = "mySqlReadInsert", method = RequestMethod.GET)
    public BaseResult<Integer> mySqlReadInsert(String name) {
        BaseResult<Integer> result = userService.insertUserRead(name);
        return result;
    }

}
