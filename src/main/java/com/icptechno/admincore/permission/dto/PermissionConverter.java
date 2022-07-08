package com.icptechno.admincore.permission.dto;

import com.icptechno.admincore.permission.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {

    final ModelMapper modelMapper;

    public PermissionConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Permission convertToEntity(PermissionDto permissionDto) {
        return modelMapper.map(permissionDto, Permission.class);
    }

    public PermissionDto convertToDto(Permission permission) {
        return modelMapper.map(permission, PermissionDto.class);
    }

}
