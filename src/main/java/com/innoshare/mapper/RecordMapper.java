package com.innoshare.mapper;

import com.innoshare.model.po.Browse;
import com.innoshare.model.po.Download;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecordMapper {
    @Insert("INSERT INTO download (user_id, paper_id, download_time, created_at, updated_at) " +
            "VALUES (#{userId}, #{paperId}, #{downloadTime}, #{createdAt}, #{updatedAt})")
            @Options(useGeneratedKeys = true, keyProperty = "downloadId")
    void addDownload(Download download);


    @Insert("INSERT INTO browse (user_id, paper_id, browse_time, created_at, updated_at) " +
            "VALUES (#{userId}, #{paperId}, #{browseTime}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "browseId")
    void addBrowse(Browse browse);

    @Select("SELECT COUNT(*) FROM download WHERE paper_id = #{paperId}")
    int getDownloadCount(Integer paperId);

    @Select("SELECT COUNT(*) FROM browse WHERE paper_id = #{paperId}")
    int getBrowseCount(Integer paperId);
}
