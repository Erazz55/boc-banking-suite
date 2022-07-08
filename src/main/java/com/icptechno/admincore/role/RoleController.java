package com.icptechno.admincore.role;

import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import com.icptechno.admincore.role.dto.CreateRoleDto;
import com.icptechno.admincore.role.dto.RoleConverter;
import com.icptechno.admincore.role.dto.RoleDto;
import com.icptechno.admincore.role.dto.UpdateRoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/role")
public class RoleController {


    final RoleConverter roleConverter;
    final RoleService roleService;

    public RoleController(RoleConverter roleConverter, RoleService roleService) {
        this.roleConverter = roleConverter;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseHolder<List<RoleDto>>> getAllRoles() {
        List<RoleDto> res = roleService.getAll().stream().map(roleConverter::convertToDto).collect(Collectors.toList());
        return ResponseBuilder.okResponseBuilder(res).build();
    }

    @PostMapping("")
    public ResponseEntity<ResponseHolder<Long>> createRole(@RequestBody CreateRoleDto roleDto) {
        // create role
        Role role = roleConverter.convertToEntity(roleDto);
        role = roleService.create(role.getName(), role.getPermissions());
        return ResponseBuilder.createdResponseBuilder(role.getId()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseHolder<Object>> updateRoleById(@PathVariable Long id, @RequestBody UpdateRoleDto roleDto) {
        Role role = roleConverter.convertToEntity(roleDto);
        roleService.updateById(id, role.getName(), role.getStatus(), role.getPermissions());
        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHolder<Object>> deleteRoleById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

}
