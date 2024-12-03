package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.model.domain.User;
import com.innoshare.model.domain.UserInfo;
import com.innoshare.model.request.UserRequest;
import com.innoshare.model.response.UserResponse;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    Response addUser(UserRequest user);

    Response getUserWithPassword(String username, String password);

    Response updateUserPassword(int userId, String password, String newPassword);

    default String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++)
        {
            password = DigestUtils.md5DigestAsHex((salt + password +
                    salt).getBytes()).toUpperCase();
        }
        return password;
    }

    UserResponse getUserResponseById(String userId);

    UserInfo getUserInfoById(String userId);

    void updateUserInfo(UserInfo userInfo);

    String updateAvatar(int userId, MultipartFile avatar) throws IOException;
}
