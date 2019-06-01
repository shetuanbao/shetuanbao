package com.stb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRE_TIME=15*60*1000;
    private static final String TOKEN_SECRET="f26e587c28064d0e855e72c0a6a0e618";

    public static String sign(Integer userId){
        Date date =new Date(System.currentTimeMillis()+EXPIRE_TIME);
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HMAC256");
            return JWT.create().withHeader(header).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        }
         catch (UnsupportedEncodingException e) {
           return null;
        }
    }
}
