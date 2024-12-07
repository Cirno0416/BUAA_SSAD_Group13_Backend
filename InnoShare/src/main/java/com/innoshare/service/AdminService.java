package com.innoshare.service;


import com.innoshare.common.Response;
import com.innoshare.model.dto.UpdateUserRequest;
import com.innoshare.model.po.AuthApplication;
import com.innoshare.model.vo.GetApplicationsResponse;
import org.springframework.util.DigestUtils;

import java.util.List;

public interface AdminService {

    /* 这个接口仅供管理员测试，实际不提供服务 */
    Response register(String username, String password);

    default String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password +
                    salt).getBytes()).toUpperCase();
        }
        return password;
    }

    Response getAdminWithPassword(String username, String password);

    Response getStatistics();

    Response getUsers(Integer page, Integer limit, Boolean isAuthenticated);

    GetApplicationsResponse getApplications(Integer page, Integer limit, Integer status);

    boolean examineApplication(Integer applicationId, Integer status, String reason);

    Response updateUser(UpdateUserRequest updateUserRequest);
}
