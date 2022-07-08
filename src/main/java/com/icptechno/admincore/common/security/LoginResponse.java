package com.icptechno.admincore.common.security;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwtToken;

    public LoginResponse() {
    }

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
