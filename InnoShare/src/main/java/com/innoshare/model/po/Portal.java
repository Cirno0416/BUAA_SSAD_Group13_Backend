package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "portals")
@Data
public class Portal {
    @TableId(type = IdType.AUTO)
    private Integer portalId;
    
    private String authorName;
    
    private String paperDois;
    
    private String coAuthors;
}