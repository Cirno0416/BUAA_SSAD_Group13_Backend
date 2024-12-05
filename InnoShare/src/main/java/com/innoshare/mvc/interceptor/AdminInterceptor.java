package com.innoshare.mvc.interceptor;

import com.innoshare.utils.CookieUtil;
import com.innoshare.utils.JWTUtil;
import io.micrometer.core.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        String token= CookieUtil.getCookie(request, "token");
        try{
            String identity= JWTUtil.getIdentity(token);
            if(!identity.equals("Admin")){
                response.getWriter().write("非管理员，权限不足");
                return false;
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.getWriter().write("error: "+e.getMessage());
        }
        return false;
    }

}
