package com.innoshare.controller;

import com.innoshare.model.po.User;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.dto.UserRequest;
import com.innoshare.model.vo.UserResponse;
import com.innoshare.service.impl.UserServiceImpl;

import com.innoshare.common.Response;
import com.innoshare.utils.CookieUtil;
import com.innoshare.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final RedissonClient redissonClient;

    @GetMapping("add")
    public Response addUser(@RequestBody UserRequest userRequest) {
        return userServiceImpl.addUser(userRequest);
    }

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Response response = userServiceImpl.getUserWithPassword(username, password);
        User user = (User)response.getData();
        if(response.getSuccess()){
            HashMap<String, String> payload = new HashMap<>();
            payload.put("username", user.getUsername());
            payload.put("userId", String.valueOf(user.getUserId()));
            try{
                String token= JWTUtil.generateToken(payload);
                ResponseCookie cookie = ResponseCookie
                        .from("token", token)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(3600)
                        .build();
                return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(response);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("changePassword")
    public Response changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        if(token == null){
            return Response.warning("请重新登录");
        }
        try{
            int userId=JWTUtil.getUserId(token);
            return userServiceImpl.updateUserPassword(userId, oldPassword, newPassword);
        }catch (UnsupportedEncodingException e){
            return Response.fatal("JWT解码故障");
        }

    }

    @GetMapping("logout")
    public Response logout(HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        try {
            Date expireDate = JWTUtil.getExpireAt(token);
            RSet<String> blackList = redissonClient.getSet("user:blacklist:"+token);
            long expirationTime = expireDate.getTime()-System.currentTimeMillis();
            blackList.add("logged out");
            blackList.expire(expirationTime, TimeUnit.MILLISECONDS);
            return Response.success("Logout success");
        } catch (Exception e){
            return Response.fatal(e.getMessage());
        }

    }

    @GetMapping("/{userId}")
    public Response getUserInfo(@PathVariable String userId) {
        try {
            UserResponse userResponse = userServiceImpl.getUserResponseById(userId);
            if (userResponse == null) {
                return Response.warning("User not found.");
            }
            return Response.success("获取用户信息成功", userResponse);
        } catch (Exception e) {
            return Response.fatal(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateUserInfo(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        try {
            int userId = JWTUtil.getUserId(token);
            UserInfo oldInfo = userServiceImpl.getUserInfoById(userId+"");
            if (oldInfo == null) {
                return Response.warning("Validation errors.");
            }

            userInfo.setUserId(userId);
            userServiceImpl.updateUserInfo(userInfo);
            return Response.success("User information updated successfully.");
        } catch (UnsupportedEncodingException e) {
            return Response.fatal("JWT解码故障");
        }
    }

    @PostMapping("/updateAvatar")
    public Response updateAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        try {
            int userId = JWTUtil.getUserId(token);
            String avatarURL = userServiceImpl.updateAvatar(userId, avatar);
            if (avatarURL == null) {
                return Response.warning("Validation errors.");
            }
            return Response.success("User avatar updated successfully.", avatarURL);
        } catch (UnsupportedEncodingException e) {
            return Response.fatal("JWT解码故障");
        } catch (IOException e) {
            return Response.fatal(e.getMessage());
        }
    }
}