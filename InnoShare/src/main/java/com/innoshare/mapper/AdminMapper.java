package com.innoshare.mapper;

import com.innoshare.model.po.Admin;
import com.innoshare.model.vo.UserResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    @Insert("INSERT INTO admin(username, password, salt) VALUES(#{username}, #{password}, #{salt})")
    void register(Admin admin);

    @Select("SELECT * FROM admin WHERE username=#{username}")
    Admin getAdminByUsername(String username);

    @Select("SELECT count(*) FROM users")
    int getTotalUsers();

    @Select("SELECT count(*) FROM users WHERE is_verified=1")
    int getTotalAuthenticatedUsers();

    @Select("SELECT count(DISTINCT doi) FROM papers")
    int getTotalAchievements();

    @Select("SELECT count(*) FROM auth_application WHERE status=0")
    int getTotalPendingAuthRequests();

    @Select("SELECT sum(download_count) FROM statistic")
    int getTotalDownloads();

    @Select("SELECT * FROM users LEFT JOIN user_info ON users.user_id = user_info.user_id " +
            "WHERE is_verified=#{isAuthenticated} LIMIT #{offset}, #{limit}")
    List<UserResponse> getUsersWithAuthenticated(int offset, int limit, boolean isAuthenticated);

    @Select("SELECT * FROM users LEFT JOIN user_info ON users.user_id = user_info.user_id " +
            "LIMIT #{offset}, #{limit}")
    List<UserResponse> getUsers(int offset, int limit);
}
