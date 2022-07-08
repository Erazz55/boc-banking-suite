package com.icptechno.admincore.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileEditDto {
    private String firstName;
    private String lastName;
    private String mobileNumber;
}
