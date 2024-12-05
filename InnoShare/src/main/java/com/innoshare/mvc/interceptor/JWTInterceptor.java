package com.innoshare.mvc.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.innoshare.utils.CookieUtil;
import com.innoshare.utils.JWTUtil;
import io.micrometer.core.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JWTInterceptor implements HandlerInterceptor {

    private final RedissonClient redissonClient;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        Map<String, Object> map=new HashMap<>();
        String token = CookieUtil.getCookie(request, "token");
        if(token==null){
            response.getWriter().write("token missing");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        if(isTokenBlacklisted(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try{
            Date expireDate=JWTUtil.getExpireAt(token);
            if(expireDate.getTime()-System.currentTimeMillis()<30*60*1000) {
                HashMap<String, String> payload=new HashMap<>();
                payload.put("username", JWTUtil.getUsername(token));
                payload.put("userId", String.valueOf(JWTUtil.getUserId(token)));
                String newToken=JWTUtil.generateToken(payload);
                ResponseCookie cookie = ResponseCookie
                        .from("token", newToken)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(3600)
                        .build();
                response.setHeader("Set-Cookie", cookie.toString());
            }
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
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private boolean isTokenBlacklisted(String token){
        RSet<String> blacklist = redissonClient.getSet("user:blacklist:"+token);
        return !blacklist.isEmpty();
    }
}
