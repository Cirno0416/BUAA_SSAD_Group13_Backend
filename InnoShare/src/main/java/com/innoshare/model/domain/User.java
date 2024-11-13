package com.innoshare.model.domain;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "users")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String username;
    private Timestamp downloadTime;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}