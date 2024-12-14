package com.innoshare.model.vo;


import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class PatentStd {
    private String id;

    private String title;

    private String assignee;

    private String author;

    private Date creationDate;

    private Date publicationDate;

    private String resultUrl;

    private String pdfUrl;

    private List<String> classification;
}


