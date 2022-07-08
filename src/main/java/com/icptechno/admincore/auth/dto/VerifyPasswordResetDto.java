package com.icptechno.admincore.auth.dto;

import lombok.Data;

@Data
public class VerifyPasswordResetDto {
    private String email;
    private String token;
}
