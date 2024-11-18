package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.model.request.UserRequest;
import org.springframework.util.DigestUtils;

public interface UserService {

    Response addUser(UserRequest user);

    Response getUserWithPassword(String username, String password);

    Response updateUserPassword(int userId, String password, String newPassword);

    Response changeInfo(String username, UserRequest user);



    default String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++)
        {
            password = DigestUtils.md5DigestAsHex((salt + password +
                    salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
