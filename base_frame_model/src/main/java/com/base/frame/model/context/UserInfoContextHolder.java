package com.base.frame.model.context;

import com.base.frame.model.dto.UserDto;

/**
 * 用户上下文信息
 */
public class UserInfoContextHolder {
    private static ThreadLocal<UserDto> userInfoContextHolder = new ThreadLocal<>();

    /**
     * 设置用户上下文
     *
     * @param user
     */
    public static void setUser(UserDto user) {
        userInfoContextHolder.set(user);
    }

    /**
     * 获取用户上下文
     *
     * @return
     */
    public static UserDto getUser() {
        UserDto userDto = userInfoContextHolder.get();
        if (userDto == null) {
            throw new RuntimeException("不存在当前用户上下文信息");
        }
        return userDto;
    }

    /**
     * 清除用户上下文
     */
    public static void clearUser() {
        userInfoContextHolder.remove();
    }

    /**
     * 是否有用户上下文
     *
     * @return
     */
    public static Boolean hasUser() {
        return userInfoContextHolder.get() != null;
    }
}
