package com.icptechno.admincore.role.dto;

import com.icptechno.admincore.common.ResourceStatus;
import com.icptechno.admincore.permission.dto.PermissionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    private ResourceStatus status;
    private List<PermissionDto> permissions;

}
