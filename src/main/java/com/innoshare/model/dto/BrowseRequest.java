package com.innoshare.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BrowseRequest {
    private Integer userId;

    private Integer paperId;

    private Date browseTime;
}
