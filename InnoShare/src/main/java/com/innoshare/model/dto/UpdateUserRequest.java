package com.innoshare.model.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String institution;

    private String fieldOfStudy;

    private String nationality;

    private String experience;

}
