package com.innoshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innoshare.model.po.UserPapers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPapersMapper extends BaseMapper<UserPapers> {
}