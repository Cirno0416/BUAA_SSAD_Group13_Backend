package com.innoshare.service;


import com.innoshare.common.Response;
import org.springframework.util.DigestUtils;

public interface AdminService {

    /* 这个接口仅供管理员测试，实际不提供服务 */
    Response register(String username, String password);

    default String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++)
        {
            password = DigestUtils.md5DigestAsHex((salt + password +
                    salt).getBytes()).toUpperCase();
        }
        return password;
    }

    Response getAdminWithPassword(String username, String password);

}
