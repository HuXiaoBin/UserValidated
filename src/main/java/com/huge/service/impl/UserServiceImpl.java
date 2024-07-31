package com.huge.service.impl;

import com.huge.mapper.UserMapper;
import com.huge.pojo.User;
import com.huge.service.UserService;
import com.huge.utils.Md5Util;
import com.huge.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        return user;
    }

    @Override
    public void register(User newUser) {
        newUser.setPassword(Md5Util.getMD5String(newUser.getPassword()));
        userMapper.register(newUser);
    }

    @Override
    public User findUserById(Integer userId) {
        User user = userMapper.findUserById(userId);
        return user;
    }

    @Override
    public void updateUser(User user) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("userId");
        user.setId(id);
        userMapper.updateUser(user);
    }
}
