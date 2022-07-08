package com.icptechno.admincore.common.security;

import com.icptechno.admincore.common.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class JwtAuthenticationController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping(value = "")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest credentials){
        final AppUserDetail userDetails = (AppUserDetail) userDetailsService.loadUserByUsername(credentials.getEmail());

        if(bCryptPasswordEncoder.matches(credentials.getPassword(), userDetails.getPassword())){
            final String token = JwtHelper.createToken(userDetails.id);
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseBuilder.builder("").code("400").message("Invalid Credentials").build();
    }
}
