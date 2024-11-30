package com.innoshare.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "papers")
@Data
public class Paper {
    @TableId(type = IdType.AUTO)
    private Integer paperId;

    private Integer userId;

    private String title;

    private String author;

    private String abstractText;

    private String keywords;

    private String filePath;

    private Date publishedAt;

    private Date createdAt;

    private Date updatedAt;

    private String doi;

    private String downloadUrl;

}