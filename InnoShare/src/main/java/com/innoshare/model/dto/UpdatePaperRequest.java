package com.innoshare.model.dto;

import lombok.Data;

@Data
public class UpdatePaperRequest {
    private Integer userId;
    private PaperRequest paperRequest;
}