package com.innoshare.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "user_info")
@Data
public class UserInfo {

    private Integer userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String institution;

    private String nationality;

    private String fieldOfStudy;

    private String experience;

    private Date createdAt;

    private Date updatedAt;
}
