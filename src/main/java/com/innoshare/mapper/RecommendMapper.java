package com.innoshare.mapper;

import com.innoshare.model.po.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendMapper {
    @Select("SELECT p.* FROM papers p " +
            "INNER JOIN (" +
            "  SELECT paper_id, COUNT(*) AS view_count " +
            "  FROM browse " +
            "  GROUP BY paper_id " +
            "  ORDER BY view_count DESC " +
            "  LIMIT #{limit} " +
            ") AS subQuery ON p.paper_id = subQuery.paper_id")
    List<Paper> getHotPaper(Integer limit);
}
