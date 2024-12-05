package com.innoshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.common.Response;
import com.innoshare.mapper.AdminMapper;
import com.innoshare.mapper.ApplicationMapper;
import com.innoshare.mapper.UserInfoMapper;
import com.innoshare.mapper.UserMapper;
import com.innoshare.model.po.Admin;
import com.innoshare.model.po.AuthApplication;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.vo.GetApplicationsResponse;
import com.innoshare.model.vo.GetUsersResponse;
import com.innoshare.model.vo.StatisticsResponse;
import com.innoshare.model.vo.UserResponse;
import com.innoshare.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    private final ApplicationMapper applicationMapper;

    private final UserMapper userMapper;

    private final UserInfoMapper userInfoMapper;

    @Override
    public Response register(String username, String password) {
        if (username == null || username.isEmpty()) {
            return Response.warning("用户名不能为空");
        }
        if (adminMapper.getAdminByUsername(username) != null) {
            return Response.warning("用户名已注册");
        }
        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        String salt= UUID.randomUUID().toString().toUpperCase();
        newAdmin.setPassword(getMd5Password(password, salt));
        newAdmin.setSalt(salt);
        adminMapper.register(newAdmin);
        return Response.success("添加成功");
    }

    @Override
    public Response getAdminWithPassword(String username, String password) {
        Admin admin = adminMapper.getAdminByUsername(username);
        if (admin == null) {
            return Response.warning("管理员用户名不存在或输入错误");
        }
        if(!getMd5Password(password, admin.getSalt()).equals(admin.getPassword())) {
            return Response.warning("密码错误");
        }
        return Response.success("登录成功", admin);
    }

    @Override
    public Response getStatistics() {
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        int totalUsers = adminMapper.getTotalUsers();
        int authenticatedUsers = adminMapper.getTotalAuthenticatedUsers();
        int totalAchievements = adminMapper.getTotalAchievements();
        int pendingAuthRequests = adminMapper.getTotalPendingAuthRequests();
        //int totalDownloads = adminMapper.getTotalDownloads();

        statisticsResponse.setTotalUsers(totalUsers);
        statisticsResponse.setAuthenticatedUsers(authenticatedUsers);
        statisticsResponse.setTotalAchievements(totalAchievements);
        statisticsResponse.setPendingAuthRequests(pendingAuthRequests);
        //statisticsResponse.setTotalDownloads(totalDownloads);

        return Response.success("Get statistics successfully", statisticsResponse);
    }

    @Override
    public Response getUsers(Integer page, Integer limit, Boolean isAuthenticated) {
        int offset = (page - 1) * limit;
        int total;
        List<UserResponse> userResponses;

        if (isAuthenticated != null) {
            if (isAuthenticated) {
                total = adminMapper.getTotalAuthenticatedUsers();
            } else {
                total = adminMapper.getTotalUsers() - adminMapper.getTotalAuthenticatedUsers();
            }
            userResponses = adminMapper.getUsersWithAuthenticated(offset, limit, isAuthenticated);
        } else {
            total = adminMapper.getTotalUsers();
            userResponses = adminMapper.getUsers(offset, limit);
        }

        GetUsersResponse getUsersResponse = new GetUsersResponse();
        getUsersResponse.setPage(page);
        getUsersResponse.setLimit(limit);
        getUsersResponse.setTotal(total);
        getUsersResponse.setUserResponses(userResponses);
        return Response.success("Get users successfully",  getUsersResponse);
    }

    @Override
    public GetApplicationsResponse getApplications(Integer page, Integer limit, Integer status) {
        int offset = (page - 1) * limit;

        List<AuthApplication> authApplications=applicationMapper.getApplications(status, offset, limit);
        int cnt= applicationMapper.countApplications(status);
        return new GetApplicationsResponse(cnt, authApplications);

    }

    @Override
    public boolean examineApplication(Integer applicationId, Integer status, String reason) {
        if(applicationMapper.updateApplicationStatus(applicationId, status, reason)==1){
            AuthApplication authApplication=applicationMapper.selectOne(new QueryWrapper<AuthApplication>().eq("application_id", applicationId));
            if(status==1) {
                userMapper.verified(authApplication.getUserId());
                UserInfo userInfo=new UserInfo(
                        authApplication.getUserId(),
                        authApplication.getFullName(),
                        authApplication.getEmail(),
                        authApplication.getPhoneNumber(),
                        authApplication.getInstitution(),
                        authApplication.getNationality(),
                        authApplication.getFieldOfStudy(),
                        "",
                        new Date(),
                        new Date());
                userInfoMapper.insert(userInfo);
            }
            return true;
        }
        return false;
    }
}
