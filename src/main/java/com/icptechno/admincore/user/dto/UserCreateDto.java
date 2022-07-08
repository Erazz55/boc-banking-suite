package com.icptechno.admincore.user.dto;

import com.icptechno.admincore.role.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String avatarLink;
    private String password;
    private RoleDto role;

}
