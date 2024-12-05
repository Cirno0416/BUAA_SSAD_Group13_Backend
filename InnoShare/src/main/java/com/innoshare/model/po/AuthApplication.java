package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName(value="auth_application")
public class AuthApplication {
    @TableId(type= IdType.AUTO)
    private Integer applicationId;

    private Integer userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String institution;

    private String nationality;

    private String idNumber;

    private String fieldOfStudy;

    private Integer status;

    private Date createdAt;

    private String docPath;

    private String reason;
}
