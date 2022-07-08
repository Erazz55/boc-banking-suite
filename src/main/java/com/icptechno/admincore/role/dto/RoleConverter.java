package com.icptechno.admincore.role.dto;

import com.icptechno.admincore.role.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    final ModelMapper modelMapper;

    public RoleConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public Role convertToEntity(CreateRoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public Role convertToEntity(UpdateRoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public Role convertToEntity(RoleShallowDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public RoleDto convertToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

}
