package com.innoshare.controller;

import com.innoshare.model.request.UserRequest;
import com.innoshare.service.impl.UserServiceImpl;

import com.innoshare.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/user/add")
    public Response addUser(@RequestBody UserRequest userRequest) {
        return userServiceImpl.addUser(userRequest);
    }
}