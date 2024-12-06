package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.dto.UserRequest;
import com.innoshare.model.vo.UserResponse;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    boolean submitApplication(int uid, String fullName, String email, String phoneNumber,String institution, String fieldOfStudy, String nationality, String idNumber, MultipartFile documents);

    boolean submitApplicationByInvitation(int uid, String inviter, String invitationCode, String fullName, String email, String phoneNumber,String institution, String fieldOfStudy, String nationality, String idNumber, MultipartFile documents);

    String getUsernameByUserId(int userId);
}
