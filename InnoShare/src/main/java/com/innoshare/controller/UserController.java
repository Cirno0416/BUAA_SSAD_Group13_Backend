package com.innoshare.controller;

import com.innoshare.model.po.User;
import com.innoshare.model.po.UserInfo;
import com.innoshare.model.dto.UserRequest;
import com.innoshare.model.vo.UserResponse;
import com.innoshare.service.UserService;

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

    private final UserService userService;
    private final RedissonClient redissonClient;

    @PostMapping("add")
    public Response addUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Response response = userService.getUserWithPassword(username, password);
        User user = (User)response.getData();
        if(response.getSuccess()){
            HashMap<String, String> payload = new HashMap<>();
            payload.put("username", user.getUsername());
            payload.put("userId", String.valueOf(user.getUserId()));
            payload.put("identity", "User");
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
        try{
            int userId=JWTUtil.getUserId(token);
            return userService.updateUserPassword(userId, oldPassword, newPassword);
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
            UserResponse userResponse = userService.getUserResponseById(userId);
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
            UserInfo oldInfo = userService.getUserInfoById(userId+"");
            if (oldInfo == null) {
                return Response.warning("Validation errors.");
            }

            userInfo.setUserId(userId);
            userService.updateUserInfo(userInfo);
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
            String avatarURL = userService.updateAvatar(userId, avatar);
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

    @PostMapping("verify")
    public Response submitApplication(@RequestParam(name = "fullName") String fullName,
                                      @RequestParam(name = "email", required = false) String email,
                                      @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                      @RequestParam(name = "institution", required = false) String institution,
                                      @RequestParam(name = "fieldOfStudy") String fieldOfStudy,
                                      @RequestParam(name = "nationality") String nationality,
                                      @RequestParam(name = "idNumber") String idNumber,
                                      @RequestParam(name = "documents") MultipartFile documents,
                                      HttpServletRequest request) {

        String token = CookieUtil.getCookie(request, "token");
        try {
            int uid = JWTUtil.getUserId(token);
            return userService.submitApplication(uid, fullName, email, phoneNumber, institution, fieldOfStudy,
                    nationality, idNumber, documents)?Response.success("申请成功"):Response.warning("申请失败");
        }catch (UnsupportedEncodingException e){
            return Response.fatal(e.getMessage());
        }
    }

    @PostMapping("verifyByCode")
    public Response submitApplication(@RequestParam(name = "inviter", required = false) String inviter,
                                      @RequestParam(name = "invitationCode", required = false) String invitationCode,
                                      @RequestParam(name = "fullName") String fullName,
                                      @RequestParam(name = "email", required = false) String email,
                                      @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                      @RequestParam(name = "institution", required = false) String institution,
                                      @RequestParam(name = "fieldOfStudy") String fieldOfStudy,
                                      @RequestParam(name = "nationality") String nationality,
                                      @RequestParam(name = "idNumber") String idNumber,
                                      @RequestParam(name = "documents") MultipartFile documents,
                                      HttpServletRequest request) {
        
        String token = CookieUtil.getCookie(request, "token");
        try {
            int uid = JWTUtil.getUserId(token);
            return userService.submitApplicationByInvitation(uid, inviter, invitationCode, fullName, email, phoneNumber,
                    institution, fieldOfStudy, nationality, idNumber, documents)?Response.success("申请成功"):Response.warning("申请失败");
        }catch (UnsupportedEncodingException e){
            return Response.fatal(e.getMessage());
        }
    }

    @GetMapping("invite")
    public Response invite(HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        try{
            int uid=JWTUtil.getUserId(token);
            String name=userService.getUserInfoById(Integer.toString(uid)).getFullName();
            return Response.success("生成邀请码成功", userService.getInvitationCode(name));
        }catch (UnsupportedEncodingException e){
            return Response.fatal(e.getMessage());
        }
    }
}