package com.innoshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.innoshare.common.Response;
import com.innoshare.mapper.ApplicationMapper;
import com.innoshare.mapper.UserMapper;
import com.innoshare.model.po.AuthApplication;
import com.innoshare.model.po.User;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.dto.UserRequest;

import com.innoshare.model.vo.UserResponse;
import com.innoshare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//继承ServiceImpl可以直接使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final ApplicationMapper applicationMapper;

    private final RedissonClient redissonClient;

    public Response addUser(UserRequest user) {
        if(user.getUsername() == null || user.getUsername().isEmpty()) {
            return Response.warning("用户名不能为空");
        }
        if(!this.getUserByName(user.getUsername()).isEmpty()) {
            return Response.warning("用户名已注册");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        String password = user.getPassword();
        String salt= UUID.randomUUID().toString().toUpperCase();
        newUser.setPassword(getMd5Password(password, salt));
        newUser.setSalt(salt);
        if(userMapper.insert(newUser) == 0) {
            return Response.error("添加失败");
        }
        return Response.success("添加成功");
    }

    @Override
    public Response getUserWithPassword(String username, String password) {
        List<User> users = getUserByName(username);
        if(users.isEmpty()) {
            return Response.warning("用户名不存在");
        }
        if(users.size() > 1) {
            return Response.error("数据库发生未知错误");
        }
        User user = users.get(0);
        if(!getMd5Password(password, user.getSalt()).equals(user.getPassword())) {
            return Response.warning("密码错误");
        }
        return Response.success("登录成功", user);
    }

    @Override
    public Response updateUserPassword(int userId, String password, String newPassword) {
        List<User> users = getUserById(userId);
        if(users.isEmpty()) {
            return Response.warning("用户不存在");
        }
        if(users.size() > 1) {
            return Response.error("数据库发生未知错误");
        }
        User user = users.get(0);
        if(!getMd5Password(password, user.getSalt()).equals(user.getPassword())) {
            return Response.warning("密码错误");
        }
        user.setPassword(getMd5Password(newPassword, user.getSalt()));
        user.setUpdatedAt(new Date());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        if(userMapper.update(user, queryWrapper)!=1){
            return Response.error("更改失败");
        }
        return Response.success("密码更改成功");
    }

    private List<User> getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userMapper.selectList(queryWrapper);
    }

    private List<User> getUserById(int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public UserResponse getUserResponseById(String userId) {
        List<User> users = getUserById(Integer.parseInt(userId));
        if (users.size() != 1) {
            return null;
        }
        User user = users.get(0);
        UserInfo userInfo = userMapper.getUserInfoById(userId);
        UserResponse userResponse = new UserResponse();

        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setIsVerified(user.getIsVerified());
        userResponse.setAvatarURL(user.getAvatarUrl());
        if (userInfo != null) {
            userResponse.setFullName(userInfo.getFullName());
            userResponse.setEmail(userInfo.getEmail());
            userResponse.setPhoneNumber(userInfo.getPhoneNumber());
            userResponse.setInstitution(userInfo.getInstitution());
            userResponse.setNationality(userInfo.getNationality());
            userResponse.setFieldOfStudy(userInfo.getFieldOfStudy());
            userResponse.setExperience(userInfo.getExperience());
        }
        return userResponse;
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdatedAt(new Date());
        userMapper.updateUserInfo(userInfo);
    }

    @Override
    public String updateAvatar(int userId, MultipartFile avatar) throws IOException {
        List<User> users = getUserById(Integer.parseInt(userId+""));
        if (users.size() != 1) {
            return null;
        }
        if (avatar == null || avatar.isEmpty()) {
            return null;
        }
        String originalFilename = avatar.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|png|jpeg)$")) {
            return null;
        }

        String baseURL = "/root/data/avatar/";
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + fileExtension;
        String avatarURL = baseURL + newFilename;
        File file = new File(avatarURL);
        avatar.transferTo(file);
        userMapper.updateAvatar(userId, avatarURL);
        return avatarURL;
    }

    @Override
    public boolean submitApplication(int uid, String fullName, String email, String phoneNumber, String institution,
                                  String fieldOfStudy, String nationality, String idNumber, MultipartFile documents) {
        AuthApplication authApplication = new AuthApplication();
        authApplication.setUserId(uid);
        authApplication.setFullName(fullName);
        authApplication.setEmail(email);
        authApplication.setPhoneNumber(phoneNumber);
        authApplication.setInstitution(institution);
        authApplication.setFieldOfStudy(fieldOfStudy);
        authApplication.setNationality(nationality);
        authApplication.setStatus(0);
        authApplication.setIdNumber(idNumber);
        authApplication.setCreatedAt(new Date());

        try {
            String baseURL = "/root/data/application/";
            String originalFilename = documents.getOriginalFilename();
            if (originalFilename == null) return false;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID() + fileExtension;
            String documentsURL = baseURL + newFilename;
            File file = new File(documentsURL);
            documents.transferTo(file);

            authApplication.setDocPath(documentsURL);
            applicationMapper.insert(authApplication);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean submitApplicationByInvitation(int uid, String inviter, String invitationCode, String fullName, String email, String phoneNumber,
                                              String institution, String fieldOfStudy, String nationality, String idNumber, MultipartFile documents) {
        RSet<String> set=redissonClient.getSet("user:auth:"+inviter);
        if(set.contains(invitationCode)){
            set.remove(invitationCode);

            AuthApplication authApplication = new AuthApplication();
            authApplication.setUserId(uid);
            authApplication.setFullName(fullName);
            authApplication.setEmail(email);
            authApplication.setPhoneNumber(phoneNumber);
            authApplication.setInstitution(institution);
            authApplication.setFieldOfStudy(fieldOfStudy);
            authApplication.setNationality(nationality);
            authApplication.setStatus(1);
            authApplication.setIdNumber(idNumber);
            authApplication.setCreatedAt(new Date());

            try {
                String baseURL = "/root/data/application/";
                String originalFilename = documents.getOriginalFilename();
                if (originalFilename == null) return false;
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID() + fileExtension;
                String documentsURL = baseURL + newFilename;
                File file = new File(documentsURL);
                documents.transferTo(file);

                authApplication.setDocPath(documentsURL);
                applicationMapper.insert(authApplication);
            }catch (IOException e){
                return false;
            }
            return true;
        }
        else return false;
    }
}