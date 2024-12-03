package com.innoshare.controller;

import com.innoshare.common.Response;
import com.innoshare.model.po.Admin;
import com.innoshare.service.impl.AdminServiceImpl;
import com.innoshare.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    /* 这个接口仅供管理员测试，实际不提供服务 */
    @PostMapping("/register")
    public Response register(@RequestParam String username, @RequestParam String password) {
        return adminServiceImpl.register(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Response response = adminServiceImpl.getAdminWithPassword(username, password);
        if (response.getSuccess()) {
            Admin admin = (Admin) response.getData();
            HashMap<String, String> payload = new HashMap<>();
            payload.put("username", admin.getUsername());
            payload.put("userId", String.valueOf(admin.getAdminId()));
            try {
                String token= JWTUtil.generateToken(payload);
                ResponseCookie cookie = ResponseCookie
                        .from("token", token)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(3600)
                        .build();
                return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(response);
            } catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/statistics")
    public Response getStatistics() {
        return adminServiceImpl.getStatistics();
    }

    @GetMapping("/users")
    public Response getUsers(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit,
                             @RequestParam(required = false) Boolean isAuthenticated) {
        return adminServiceImpl.getUsers(page, limit, isAuthenticated);
    }
}
