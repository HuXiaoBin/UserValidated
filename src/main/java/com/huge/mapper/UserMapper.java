package com.huge.mapper;

import com.huge.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 通过username查找用户
    @Select("select * from user where userName=#{username}")
    User findUserByName(String userName);

    // 用户注册
    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    void register(User newUser);

    // 通过Id查找用户
    @Select("select * from user where id=#{userId}")
    User findUserById(Integer userId);

    // 更新用户信息
    @Update("update user set nickname=#{nickname},user_pic=#{userPic},update_time=now() where id=#{id}")
    void updateUser(User user);
}
