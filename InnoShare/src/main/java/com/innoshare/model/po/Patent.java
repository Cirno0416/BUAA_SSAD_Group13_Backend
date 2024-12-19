package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@TableName(value = "patents")
@Data
public class Patent {
    @TableId
    private String id;

    private String title;

    private String assignee;

    private String author;

    private Date creationDate;

    private Date publicationDate;

    private String resultUrl;

    private String pdfUrl;

    private String classification;

    @TableField("abstract")
    private String abstractText;

    private String timeline;
}
