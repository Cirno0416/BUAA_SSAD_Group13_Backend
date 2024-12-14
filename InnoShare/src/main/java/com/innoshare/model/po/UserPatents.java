package com.innoshare.model.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "user_patents")
@Data
public class UserPatents {
    private Integer userId;

    private String patentId;
}


