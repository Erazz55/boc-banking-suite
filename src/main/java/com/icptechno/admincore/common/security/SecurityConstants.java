package com.icptechno.admincore.common.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_HEADER_STRING = "Authorization";
    public static final String REFRESH_HEADER_STRING = "X-Refresh-Token";
    public static final String CLAIM_USER_ID = "USER_ID";

    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_URL = "/auth/register";
    public static final String REFRESH_URL = "/auth/refresh";
    public static final String PASSWORD_RESET_REQUEST_URL = "/auth/password/request";
    public static final String PASSWORD_RESET_VERIFY_URL = "/auth/password/verify";
    public static final String PASSWORD_RESET_URL = "/auth/password/reset";
}