package com.innoshare.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaperRequest {
    private String title;
    private String author;
    private String abstractText;
    private List<String> subjects;
    private Date publishedAt;
    //private String doi;
    //private String downloadUrl;
    private String doi;//"2401.01098"
    private List<ReferenceRequest> references;
}
