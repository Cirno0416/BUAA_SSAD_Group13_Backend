package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.model.request.UserRequest;
import org.springframework.util.DigestUtils;

public interface UserService {

    public Response addUser(UserRequest user);

    public boolean existUser(String username);

    default String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++)
        {
            password = DigestUtils.md5DigestAsHex((salt + password +
                    salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
