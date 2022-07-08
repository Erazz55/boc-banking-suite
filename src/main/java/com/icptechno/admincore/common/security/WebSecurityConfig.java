package com.icptechno.admincore.common.security;

import com.icptechno.admincore.auth.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.icptechno.admincore.common.security.SecurityConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
                .antMatchers(HttpMethod.POST, REFRESH_URL).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_REQUEST_URL).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_VERIFY_URL).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-docs").permitAll()

                // roles api
//                .antMatchers(HttpMethod.POST, "/role/", "/role").hasAuthority("ROLE_CREATE")
                .antMatchers(HttpMethod.PUT, "/role/{\\d+}").hasAuthority("ROLE_EDIT")
                .antMatchers(HttpMethod.DELETE, "/role/{\\d+}").hasAuthority("ROLE_DELETE")

                // user api
//                .antMatchers(HttpMethod.POST, "/user/", "/user").hasAuthority("USER_CREATE")
                .antMatchers(HttpMethod.PUT, "/user/profile").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/profile/avatar").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/{\\d+}").hasAuthority("USER_EDIT")
                .antMatchers(HttpMethod.DELETE, "/user/{\\d+}").hasAuthority("USER_DELETE")

                // image api
                .antMatchers(HttpMethod.GET, "/image/*").permitAll()

//                .antMatchers("/permission/list").hasAnyAuthority("USER_READ")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenService, objectMapper))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, tokenService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration = configuration.applyPermitDefaultValues();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Collections.singletonList("*"));
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.addAllowedOrigin("*");
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedOrigins(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList(ACCESS_HEADER_STRING, REFRESH_HEADER_STRING));
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
