package com.innoshare.model.dto;

import com.innoshare.model.vo.PatentStd;
import lombok.Data;

@Data
public class PatentRequest {
    private Integer userId;
    private PatentStd patent;
}

