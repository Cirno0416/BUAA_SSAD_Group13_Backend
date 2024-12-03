package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "statistic")
@Data
public class Statistic {
    @TableId(type = IdType.AUTO)
    private Integer statId;

    private Integer paperId;

    private Integer viewCount;

    private Integer downloadCount;

    private Date createdAt;

    private Date updatedAt;

}