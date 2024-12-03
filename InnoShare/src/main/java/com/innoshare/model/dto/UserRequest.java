package com.innoshare.model.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Integer userId;
    private String username;
    private String password;
}
