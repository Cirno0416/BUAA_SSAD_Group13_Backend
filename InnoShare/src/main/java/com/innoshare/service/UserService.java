package com.innoshare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.innoshare.common.Response;
import com.innoshare.mapper.UserMapper;
import com.innoshare.model.domain.User;
import com.innoshare.model.request.UserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//继承ServiceImpl可以直接使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    
    @Autowired
    private UserMapper userMapper;

    public Response addUser(UserRequest user) {
        if(user.getUsername() == null || user.getUsername().isEmpty()) {
            return Response.warning("用户名不能为空");
        }
        if(this.existUser(user.getUsername())) {
            return Response.warning("用户名已注册");
        }
        if(userMapper.insert(user) == 0) {
            return Response.error("添加失败");
        }
        return Response.success("添加成功");
    }


    public boolean existUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    
} 