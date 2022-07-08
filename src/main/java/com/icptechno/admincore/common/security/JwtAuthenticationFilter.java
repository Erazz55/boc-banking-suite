package com.icptechno.admincore.common.security;

import com.icptechno.admincore.auth.TokenService;
import com.icptechno.admincore.auth.dto.RefreshTokenDto;
import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    final private TokenService tokenService;
    final private AuthenticationManager authenticationManager;
    final private ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest request1 = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request1.getEmail(),
                    request1.getPassword(),
                    new ArrayList<>());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AppUserDetail userDetail = (AppUserDetail) authResult.getPrincipal();
        Long id = userDetail.getId();
        String accessToken = tokenService.createAccessToken(id);
        String refreshToken = tokenService.createRefreshToken(id);

//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.addHeader(ACCESS_HEADER_STRING, TOKEN_PREFIX + accessToken);
//        response.addHeader(REFRESH_HEADER_STRING, refreshToken);

        response.setHeader("Content-Type", "application/json");

        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setAccessToken(accessToken);
        refreshTokenDto.setRefreshToken(refreshToken);
        ResponseEntity<ResponseHolder<RefreshTokenDto>> e = ResponseBuilder.okResponseBuilder(refreshTokenDto).build();

        String res = objectMapper.writeValueAsString(e.getBody());

        response.getWriter().write(res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        System.out.println(failed);
//        failed.printStackTrace();
//        response.getWriter().write
        throw new BadCredentialsException("Invalid Credentials");
    }
}
