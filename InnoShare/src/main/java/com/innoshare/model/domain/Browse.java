package com.innoshare.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "browse")
@Data
public class Browse {
    @TableId(type = IdType.AUTO)
    private Integer browseId;

    private Integer userId;

    private Integer paperId;

    private Date browseTime;

    private Date createdAt;

    private Date updatedAt;


}