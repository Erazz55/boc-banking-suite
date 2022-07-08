package com.icptechno.admincore.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.icptechno.admincore.common.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class TokenService {

    private static final String CLAIM_USER_ID = "userId";

    final TokenRepository tokenRepository;

    @Value("${app.token.secret}")
    private String tokenSecret;
    @Value("${app.token.access.expiry}")
    private int accessExpiryTime;
    @Value("${app.token.refresh.expiry}")
    private int refreshExpiryTime;
    @Value("${app.token.reset.expiry}")
    private int resetExpiryTime;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private Date getExpiresAt(int expirySeconds) {
        return new Date(System.currentTimeMillis() + (expirySeconds * 1000));
    }

    private String createToken(TokenType type, Long userId, int tokenLength, int expiryTime) {
        Date expiresAt = getExpiresAt(expiryTime);
        String tokenString = CryptoUtil.getRandomString(tokenLength);
        Token token = new Token();
        token.setExpiresAt(expiresAt);
        token.setTokenId(new TokenId(type, CryptoUtil.sha1Hash(tokenString), userId));
        tokenRepository.save(token);
        return tokenString;
    }

    private void removeToken(TokenType type, Long userId, String tokenString) {
        tokenRepository.deleteById(new TokenId(type, CryptoUtil.sha1Hash(tokenString), userId));
    }

    private boolean isTokenValid(TokenType type, Long userId, String tokenString) {
        return tokenRepository.findById(new TokenId(type, CryptoUtil.sha1Hash(tokenString), userId)).map(t -> {
            Date timeNow = new Date();
            return t.getExpiresAt().after(timeNow);
        }).orElse(false);
    }

    private boolean isValidJwt(String token) {
        try {
            JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT decodeJwt(String token) {
        return JWT.decode(token);
    }

    /* ACCESS TOKEN */

    public String createAccessToken(Long userId) {
        Date expiresAt = getExpiresAt(accessExpiryTime);

        String accessToken = JWT.create().withSubject(userId.toString())
                .withClaim(CLAIM_USER_ID, userId)
                .withExpiresAt(expiresAt)
                .sign(HMAC512(tokenSecret.getBytes()));

//        Token token = new Token();
//        token.setExpiresAt(expiresAt);
//        token.setTokenId(new TokenId(TokenType.ACCESS, sha1Hash(accessToken), id));
//        tokenRepository.save(token);

        return accessToken;
    }

    public boolean isAccessTokenValid(String token) {
        return isValidJwt(token);
    }

    public Long getUserIdFromAccessToken(String token) {
        DecodedJWT jwt = decodeJwt(token);
        return jwt.getClaim(CLAIM_USER_ID).asLong();
    }

    /* REFRESH TOKEN */

    public String createRefreshToken(Long userId) {
        return createToken(TokenType.REFRESH, userId, 32, refreshExpiryTime);
    }

    public boolean isRefreshTokenValid(Long userId, String refreshToken) {
        return isTokenValid(TokenType.REFRESH, userId, refreshToken);
    }

    public void removeRefreshToken(Long userId, String refreshToken) {
        removeToken(TokenType.REFRESH, userId, refreshToken);
    }

    /* PASSWORD RESET TOKEN */

    public String createPasswordResetToken(Long userId) {
        return createToken(TokenType.PASSWORD_RESET, userId, 20, resetExpiryTime);
    }

    public boolean isPasswordResetTokenValid(Long userId, String resetToken) {
        return isTokenValid(TokenType.PASSWORD_RESET, userId, resetToken);
    }

    public void removePasswordResetToken(Long userId, String resetToken) {
        removeToken(TokenType.PASSWORD_RESET, userId, resetToken);
    }

}
