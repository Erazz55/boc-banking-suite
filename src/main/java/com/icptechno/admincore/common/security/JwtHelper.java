package com.icptechno.admincore.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.icptechno.admincore.common.security.SecurityConstants.*;

public abstract class JwtHelper {

    public static String createToken(Long userId) throws JWTCreationException {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim(CLAIM_USER_ID, userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public static DecodedJWT decodeToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token);
    }

    public static Long getUserId(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(CLAIM_USER_ID).asLong();
    }

}
