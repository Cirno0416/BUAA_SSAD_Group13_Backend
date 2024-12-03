package com.innoshare.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetUsersResponse {

    private int page;

    private int limit;

    private int total;

    private List<UserResponse> userResponses;

}
