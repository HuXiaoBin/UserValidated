package com.huge.service;

import com.huge.pojo.User;

public interface UserService {
    // 通过username查找用户
    User findUserByName(String userName);

    // 注册用户
    void register(User newUser);

    // 通过Id查找用户
    User findUserById(Integer userId);

    // 更新用户信息
    void updateUser(User user);
}
