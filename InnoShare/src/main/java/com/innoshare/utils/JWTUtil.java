package com.innoshare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String secret="2024SSAD";
    private static final int expireTime=60;

    public static String generateToken(HashMap<String, String> map) throws UnsupportedEncodingException {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, expireTime);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public static Map<String, Claim> getToken(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getClaims();
    }

    public static String getUsername(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getClaim("username").asString();
    }
    public static int getUserId(String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        int id = Integer.parseInt(jwt.getClaim("userId").asString());
        return id;
    }

}
