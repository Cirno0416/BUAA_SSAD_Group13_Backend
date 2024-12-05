package com.innoshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innoshare.model.po.AuthApplication;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplicationMapper extends BaseMapper<AuthApplication> {

    @Select("SELECT * FROM auth_application WHERE status=#{status} ORDER BY created_at DESC LIMIT #{offset}, #{limit}")
    List<AuthApplication> getApplications(int status, int offset, int limit);

    @Select("SELECT COUNT(*) FROM auth_application where status=#{status}")
    int countApplications(int status);

    @Update("UPDATE auth_application SET status=#{status}, reason=#{reason} WHERE application_id=#{applicationId}")
    int updateApplicationStatus(int applicationId, int status, String reason);
}
