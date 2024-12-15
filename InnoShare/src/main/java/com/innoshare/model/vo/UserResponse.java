package com.innoshare.model.vo;


import lombok.Data;

@Data
public class UserResponse {
    private Integer userId;

    private String username;

    private Integer isVerified;

    private String avatarURL;

    private String email;

    private String phoneNumber;

    private String fullName;

    private String institution;

    private String nationality;

    private String fieldOfStudy;

    private String experience;

}
