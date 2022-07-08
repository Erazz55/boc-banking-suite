package com.icptechno.admincore.auth.dto;

import lombok.Data;

@Data
public class PasswordResetDto {

    private String email;
    private String token;

    private String password;

}
