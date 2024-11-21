package com.innoshare.mvc.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.innoshare.utils.CookieUtil;
import com.innoshare.utils.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map=new HashMap<>();
        String token = CookieUtil.getCookie(request, "token");
        try{
            JWTUtil.getToken(token);
            return true;
        }catch (SignatureVerificationException e){
            System.out.println(e.getMessage());
            map.put("error", "Invalid Signature");
        }catch (TokenExpiredException e){
            System.out.println(e.getMessage());
            map.put("error", "Token Expired");
        }catch (AlgorithmMismatchException e){
            System.out.println(e.getMessage());
            map.put("error", "Algorithm Mismatch");
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("error", "token error");
        }
        map.put("state", false);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(map);
        return false;
    }
}
