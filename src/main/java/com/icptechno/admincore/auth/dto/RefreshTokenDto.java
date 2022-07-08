package com.icptechno.admincore.auth.dto;

import lombok.Data;

@Data
public class RefreshTokenDto {

    private String refreshToken;
    private String accessToken;

}
