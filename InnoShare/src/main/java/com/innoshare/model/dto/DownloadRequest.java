package com.innoshare.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DownloadRequest {
    private Integer userId;
    private Integer paperId;
    private Date downloadTime;
}
