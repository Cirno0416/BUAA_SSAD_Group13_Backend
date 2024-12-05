package com.innoshare.mvc;

import com.innoshare.mvc.interceptor.AdminInterceptor;
import com.innoshare.mvc.interceptor.JWTInterceptor;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final RedissonClient redissonClient;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(redissonClient))
                .addPathPatterns("/users/**")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/users/login")
                .excludePathPatterns("/users/add")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/register");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/register");
    }
}
