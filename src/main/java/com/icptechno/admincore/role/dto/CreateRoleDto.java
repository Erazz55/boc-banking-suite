package com.icptechno.admincore.role.dto;

import com.icptechno.admincore.permission.dto.PermissionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateRoleDto {

    private String name;
    private List<PermissionDto> permissions;

}
