package com.icptechno.admincore.role.dto;

import com.icptechno.admincore.common.ResourceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleShallowDto {
    private Long id;
    private String name;
    private ResourceStatus status;
}
