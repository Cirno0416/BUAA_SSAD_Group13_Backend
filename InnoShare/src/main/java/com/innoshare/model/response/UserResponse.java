package com.innoshare.model.response;


import lombok.Data;

@Data
public class UserResponse {
    private Integer userId;

    private String username;

    private Boolean isVerified;

    private String email;

    private String phoneNumber;

    private String fullName;

    private String institution;

    private String nationality;

    private String fieldOfStudy;

    private String experience;

}
