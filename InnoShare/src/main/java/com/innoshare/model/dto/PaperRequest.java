package com.innoshare.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaperRequest {
    private String title;
    private String author;
    private String abstractText;
    private String subjects;
    private Date publishedAt;
    private String doi;
    private String downloadUrl;
    private List<ReferenceRequest> references;
}
