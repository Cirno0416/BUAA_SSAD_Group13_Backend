package com.innoshare.model.response;


import lombok.Data;

@Data
public class UserResponse {
    private Integer userId;

    private String username;

    private String email;

    private String fullName;

    private String institution;

    private String fieldOfStudy;

    private String experience;

    private Boolean isVerified;

}
