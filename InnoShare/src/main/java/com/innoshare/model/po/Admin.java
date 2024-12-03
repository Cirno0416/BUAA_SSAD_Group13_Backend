package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "admin")
@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Integer adminId;

    private String username;

    private String password;

    private String salt;

    private Date created_at;

    private Date updated_at;
}
