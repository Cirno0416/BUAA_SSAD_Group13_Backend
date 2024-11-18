package com.innoshare.controller;

import com.innoshare.model.domain.User;
import com.innoshare.model.request.UserRequest;
import com.innoshare.service.impl.UserServiceImpl;

import com.innoshare.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("add")
    public Response addUser(@RequestBody UserRequest userRequest) {
        return userServiceImpl.addUser(userRequest);
    }

    @GetMapping("login")
    public Response login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Response response = userServiceImpl.getUserWithPassword(username, password);
        User user = (User)response.getData();
        if(response.getSuccess()){
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getUserId());
        }
        return response;
    }

    @PutMapping("changePassword")
    public Response changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session) {
        return userServiceImpl.updateUserPassword((Integer) session.getAttribute("userId"), oldPassword, newPassword);
    }
}