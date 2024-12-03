package com.innoshare.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "users")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String username;

    private String password;

    private Boolean isVerified;

    private String salt;

    private String avatarURL;

    private Date createdAt;

    private Date updatedAt;

}