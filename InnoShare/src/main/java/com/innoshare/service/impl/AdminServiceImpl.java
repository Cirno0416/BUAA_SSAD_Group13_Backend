package com.innoshare.service.impl;

import com.innoshare.common.Response;
import com.innoshare.mapper.AdminMapper;
import com.innoshare.model.po.Admin;
import com.innoshare.model.po.User;
import com.innoshare.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public Response register(String username, String password) {
        if (username == null || username.isEmpty()) {
            return Response.warning("用户名不能为空");
        }
        if (adminMapper.getAdminByUsername(username) != null) {
            return Response.warning("用户名已注册");
        }
        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        String salt= UUID.randomUUID().toString().toUpperCase();
        newAdmin.setPassword(getMd5Password(password, salt));
        newAdmin.setSalt(salt);
        adminMapper.register(newAdmin);
        return Response.success("添加成功");
    }

    @Override
    public Response getAdminWithPassword(String username, String password) {
        Admin admin = adminMapper.getAdminByUsername(username);
        if (admin == null) {
            return Response.warning("管理员用户名不存在或输入错误");
        }
        if(!getMd5Password(password, admin.getSalt()).equals(admin.getPassword())) {
            return Response.warning("密码错误");
        }
        return Response.success("登录成功", admin);
    }

}
