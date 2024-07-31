package com.huge.pojo;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Integer id;//主键ID

    @Pattern(regexp = "^\\S{5,16}$")
    private String username;//用户名

    @Pattern(regexp = "^\\S{5,16}$")
    @JsonIgnore // 让springmvc把当前对象转换成json字符串的时候,忽略password,最终的json字符串中就没有password这个属性了
    private String password;//密码

    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    private String userPic;//用户头像地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Login extends Default{

    }

    public interface Register extends Default {

    }

    public interface Update{

    }
}
