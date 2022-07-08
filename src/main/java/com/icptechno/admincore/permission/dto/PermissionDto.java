package com.icptechno.admincore.permission.dto;

import com.icptechno.admincore.common.ResourceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDto {

    private Long id;
    private String name;
    private String value;
    private ResourceStatus status;

}

