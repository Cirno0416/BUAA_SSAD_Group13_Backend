package com.innoshare.mapper;

import com.innoshare.model.dto.UpdateUserRequest;
import com.innoshare.model.po.Admin;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.vo.UserResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdminMapper {

    // 注册
    @Insert("INSERT INTO admin(username, password, salt) VALUES(#{username}, #{password}, #{salt})")
    void register(Admin admin);

    // 根据用户名获取管理员
    @Select("SELECT * FROM admin WHERE username=#{username}")
    Admin getAdminByUsername(String username);

    // 获取用户总数量
    @Select("SELECT count(*) FROM users")
    int getTotalUsers();

    // 获取认证用户总数量
    @Select("SELECT count(*) FROM users WHERE is_verified=1")
    int getTotalAuthenticatedUsers();

    // 获取论文总数量
    @Select("SELECT count(DISTINCT doi) FROM papers")
    int getTotalPapers();

    // 获取专利总数量
    @Select("SELECT count(*) FROM patents")
    int getTotalPatents();

    // 获取待处理认证请求总数量
    @Select("SELECT count(*) FROM auth_application WHERE status=0")
    int getTotalPendingAuthRequests();

    // 获取下载总数量
    @Select("SELECT IFNULL(sum(download_count), 0) FROM statistic")
    int getTotalDownloads();

    // 获取浏览总数量
    @Select("SELECT IFNULL(sum(view_count), 0) FROM statistic")
    int getTotalBrowse();

    // 最近一天新增的用户数量
    @Select("SELECT count(*) FROM users WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)")
    int getTotalDailyNewUsers();

    // 最近一周新增的用户数量
    @Select("SELECT count(*) FROM users WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)")
    int getTotalWeeklyNewUsers();

    // 最近一月新增的用户数量
    @Select("SELECT count(*) FROM users WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)")
    int getTotalMonthlyNewUsers();

    // 最近一天活跃的用户数量
    @Select("SELECT count(DISTINCT user_id) FROM (" +
            "   SELECT user_id FROM browse WHERE browse_time >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +
            "   UNION " +
            "   SELECT user_id FROM download WHERE download_time >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +
            ") AS active_users")
    int getTotalDailyActiveUsers();

    // 最近一周活跃的用户数量
    @Select("SELECT count(DISTINCT user_id) FROM (" +
            "   SELECT user_id FROM browse WHERE browse_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "   UNION " +
            "   SELECT user_id FROM download WHERE download_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            ") AS active_users")
    int getTotalWeeklyActiveUsers();

    // 最近一月活跃的用户数量
    @Select("SELECT count(DISTINCT user_id) FROM (" +
            "   SELECT user_id FROM browse WHERE browse_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "   UNION " +
            "   SELECT user_id FROM download WHERE download_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            ") AS active_users")
    int getTotalMonthlyActiveUsers();

    // 最近四周每周新增用户数量
    @Select("SELECT weeks.week AS week, count(u.user_id) AS new_user_count " +
            "FROM (" +
            "    SELECT 'Week 1' AS week, DATE_SUB(CURDATE(), INTERVAL 7 DAY) AS start_date, CURDATE() AS end_date" +
            "    UNION ALL" +
            "    SELECT 'Week 2', DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY)" +
            "    UNION ALL" +
            "    SELECT 'Week 3', DATE_SUB(CURDATE(), INTERVAL 21 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY)" +
            "    UNION ALL" +
            "    SELECT 'Week 4', DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 21 DAY)" +
            ") AS weeks " +
            "LEFT JOIN users u" +
            "    ON u.created_at >= weeks.start_date " +
            "    AND u.created_at < weeks.end_date " +
            "GROUP BY weeks.week " +
            "ORDER BY FIELD(weeks.week, 'Week 1', 'Week 2', 'Week 3', 'Week 4')")
    List<Map<String, Object>> getRecentFourWeeksNewUsers();

    // 最近四周每周活跃用户数量
    @Select("SELECT weeks.week AS week, count(DISTINCT u.user_id) AS active_user_count " +
            "FROM (" +
            "    SELECT 'Week 1' AS week, DATE_SUB(CURDATE(), INTERVAL 7 DAY) AS start_date, CURDATE() AS end_date" +
            "    UNION ALL" +
            "    SELECT 'Week 2', DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY)" +
            "    UNION ALL" +
            "    SELECT 'Week 3', DATE_SUB(CURDATE(), INTERVAL 21 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY)" +
            "    UNION ALL" +
            "    SELECT 'Week 4', DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 21 DAY)" +
            ") AS weeks " +
            "LEFT JOIN (" +
            "    SELECT DISTINCT user_id, browse_time AS action_time " +
            "    FROM browse" +
            "    UNION ALL" +
            "    SELECT DISTINCT user_id, download_time AS action_time " +
            "    FROM download" +
            ") AS u" +
            "    ON u.action_time >= weeks.start_date " +
            "    AND u.action_time < weeks.end_date " +
            "GROUP BY weeks.week " +
            "ORDER BY FIELD(weeks.week, 'Week 1', 'Week 2', 'Week 3', 'Week 4')")
    List<Map<String, Object>> getRecentFourWeeksActiveUsers();

    // 获取指定数量的认证（/未认证）用户信息
    @Select("SELECT * FROM users LEFT JOIN user_info ON users.user_id = user_info.user_id " +
            "WHERE is_verified=#{isAuthenticated} LIMIT #{offset}, #{limit}")
    List<UserResponse> getUsersWithAuthenticated(int offset, int limit, boolean isAuthenticated);

    // 获取指定数量的用户信息
    @Select("SELECT * FROM users LEFT JOIN user_info ON users.user_id = user_info.user_id " +
            "LIMIT #{offset}, #{limit}")
    List<UserResponse> getUsers(int offset, int limit);

    // 根据id获取userInfo
    @Select("SELECT * FROM user_info WHERE user_id=#{userId}")
    UserInfo getUserInfoById(String userId);

    // 管理员更新用户信息
    void updateUser(UpdateUserRequest updateUserRequest);
}
