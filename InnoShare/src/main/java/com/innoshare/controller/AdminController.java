package com.innoshare.controller;

import com.innoshare.common.Response;
import com.innoshare.model.dto.UpdateUserRequest;
import com.innoshare.model.po.Admin;
import com.innoshare.service.AdminService;
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

    private final AdminService adminService;

    /* 这个接口仅供管理员测试，实际不提供服务 */
    @PostMapping("/register")
    public Response register(@RequestParam String username, @RequestParam String password) {
        return adminService.register(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Response response = adminService.getAdminWithPassword(username, password);
        if (response.getSuccess()) {
            Admin admin = (Admin) response.getData();
            HashMap<String, String> payload = new HashMap<>();
            payload.put("username", admin.getUsername());
            payload.put("userId", String.valueOf(admin.getAdminId()));
            payload.put("identity", "Admin");
            try {
                String token= JWTUtil.generateToken(payload);
                ResponseCookie cookie = ResponseCookie
                        .from("token", token)
                        .httpOnly(true)
                        .secure(false)
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

    @GetMapping("auth-requests")
    public Response getAuthRequests(@RequestParam(name = "status") Integer status,
                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return Response.success("获取成功", adminService.getApplications(page, limit, status));
    }

    @PostMapping("auth-requests/{authRequestId}")
    public Response updateAuthRequest(@PathVariable("authRequestId") Integer authRequestId,
                                      @RequestParam("status") Integer status,
                                      @RequestParam("reason") String reason){
        return adminService.examineApplication(authRequestId, status, reason)?Response.success("更新成功"): Response.error("更新失败");
    }


    @GetMapping("/statistics")
    public Response getStatistics() {
        return adminService.getStatistics();
    }

    @GetMapping("/users")
    public Response getUsers(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit,
                             @RequestParam(required = false) Boolean isAuthenticated) {
        return adminService.getUsers(page, limit, isAuthenticated);
    }

    @PostMapping("/users/update")
    public Response updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return adminService.updateUser(updateUserRequest);
    }
}
