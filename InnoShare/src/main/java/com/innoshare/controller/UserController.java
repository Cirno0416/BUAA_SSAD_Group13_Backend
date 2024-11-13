package com.innoshare.controller;

import com.innoshare.model.request.UserRequest;
import com.innoshare.service.UserService;

import com.innoshare.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/add")
    public Response addUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }
}