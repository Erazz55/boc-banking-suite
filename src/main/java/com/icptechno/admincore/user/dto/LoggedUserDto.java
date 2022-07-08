package com.icptechno.admincore.user.dto;

import com.icptechno.admincore.role.dto.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoggedUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private RoleDto role;
    private String avatarLink;
    private String status;

}
