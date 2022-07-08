package com.icptechno.admincore.user.dto;

import com.icptechno.admincore.role.dto.RoleShallowDto;
import com.icptechno.admincore.user.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private RoleShallowDto role;
    private String avatarLink;
    private UserStatus status;

}
