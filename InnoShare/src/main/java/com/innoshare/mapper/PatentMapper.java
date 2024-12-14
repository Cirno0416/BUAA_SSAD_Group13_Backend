
package com.innoshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innoshare.model.po.Patent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatentMapper extends BaseMapper<Patent> {
}