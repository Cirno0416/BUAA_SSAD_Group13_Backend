package com.innoshare.model.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName(value = "user_papers")
@Data
public class UserPapers {
    private Integer userId;

    private Integer paperId;
}
