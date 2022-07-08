package com.icptechno.admincore.common.security;

import com.icptechno.admincore.auth.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.icptechno.admincore.common.security.SecurityConstants.ACCESS_HEADER_STRING;
import static com.icptechno.admincore.common.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    final private UserDetailsServiceImpl userDetailsService;
    final private TokenService tokenService;
    Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authManager, UserDetailsServiceImpl userDetailsService, TokenService tokenService) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(ACCESS_HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(ACCESS_HEADER_STRING);
        if (token == null) return null;
        token = token.replace(TOKEN_PREFIX, "");
        try {
            if (!tokenService.isAccessTokenValid(token)) {
                throw new Exception("Invalid Token");
            }
            Long userId = tokenService.getUserIdFromAccessToken(token);
            AppUserDetail userDetail = (AppUserDetail) userDetailsService.loadUserById(userId);
            return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}