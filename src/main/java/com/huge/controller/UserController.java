package com.huge.controller;

import com.huge.pojo.Result;
import com.huge.pojo.User;
import com.huge.service.UserService;
import com.huge.utils.AliOssUtil;
import com.huge.utils.JwtUtil;
import com.huge.utils.Md5Util;
import com.huge.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<String> UserLogin(@Validated User lgUser) {

        User user = userService.findUserByName(lgUser.getUsername());
        if (user == null) {
            return Result.error("用户名不存在");
        }

        if (user.getPassword().equals(Md5Util.getMD5String(lgUser.getPassword()))) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.genToken(claims);
            // 把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(user.getId().toString(), token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @PostMapping("/register")
    public Result UserRegister(@Validated User newUser) {
        User user = userService.findUserByName(newUser.getUsername());
        if (user != null) {
            return Result.error("用户名已被占用");
        }

        userService.register(newUser);

        return Result.success();
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader("Authorization") String token) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        User user = userService.findUserById(userId);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result updateUser(@Pattern(regexp = "^\\S{1,10}$")String nickname, MultipartFile avatarFile) throws Exception {

        User user = new User();
        if (avatarFile!=null && avatarFile.getSize()>0) {
            //把文件的内容存储到本地磁盘上
            String originalFilename = avatarFile.getOriginalFilename();
            //保证文件的名字是唯一的,从而防止文件覆盖
            String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
            String avatarUrl = AliOssUtil.uploadFile(filename,avatarFile.getInputStream());
            user.setUserPic(avatarUrl);
        }
        user.setNickname(nickname);
        userService.updateUser(user);
        return Result.success();
    }
}
