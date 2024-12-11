package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

@TableName(value = "papers")
@Data
public class Paper {
    @TableId(type = IdType.AUTO)
    private Integer paperId;

    private Integer userId;

    private String doi;

    private String title;

    private String author;

    private String abstractText;

    private String subject;

    private String filePath;

    private String downloadUrl;

    private Date publishedAt;

    private Date createdAt;

    private Date updatedAt;

    private Integer citationCount;

    private Integer downloadCount;

}