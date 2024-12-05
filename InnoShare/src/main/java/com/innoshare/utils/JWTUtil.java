package com.innoshare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    public static DecodedJWT getDecodedToken(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

    public static String getUsername(String token) throws UnsupportedEncodingException {
        return getDecodedToken(token).getClaim("username").asString();
    }

    public static int getUserId(String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = getDecodedToken(token);
        return Integer.parseInt(jwt.getClaim("userId").asString());
    }

    public static String getIdentity(String token) throws UnsupportedEncodingException {

        return getDecodedToken(token).getClaim("identity").asString();
    }

    public static Date getExpireAt(String token) throws UnsupportedEncodingException {
        return getDecodedToken(token).getExpiresAt();
    }

}
