package com.huge.pojo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;//主键ID
    private String title;//标题
    private String content;//内容
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
