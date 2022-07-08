package com.icptechno.admincore.user.dto;

import com.icptechno.admincore.user.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserShallowDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String avatarLink;
    private UserStatus status;

}
