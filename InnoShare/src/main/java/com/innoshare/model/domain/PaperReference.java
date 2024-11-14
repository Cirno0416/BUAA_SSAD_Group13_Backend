package com.innoshare.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "paper_reference")
@Data
public class PaperReference {
    @TableId(type = IdType.AUTO)
    private Integer referenceId;

    private Integer citingPaperId;

    private Integer citedPaperId;

    private Date createdAt;

    private Date updatedAt;

}