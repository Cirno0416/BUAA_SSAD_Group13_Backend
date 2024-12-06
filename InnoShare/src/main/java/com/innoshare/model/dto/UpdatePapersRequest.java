package com.innoshare.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePapersRequest {
    private Integer userId;
    private List<PaperRequest> paperRequests;
}
