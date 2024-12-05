package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value = "user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @TableId
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
