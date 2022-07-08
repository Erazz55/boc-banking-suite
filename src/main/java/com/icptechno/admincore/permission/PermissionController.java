package com.icptechno.admincore.permission;

import com.icptechno.admincore.common.ResponseHolder;
import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.permission.dto.PermissionConverter;
import com.icptechno.admincore.permission.dto.PermissionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/permission")
public class PermissionController {

    private final PermissionConverter permissionConverter;
    private final PermissionService permissionService;

    public PermissionController(PermissionConverter permissionConverter, PermissionService permissionService) {
        this.permissionConverter = permissionConverter;
        this.permissionService = permissionService;
    }

//    @GetMapping(value = "", params = { "page", "size" })
//    public ResponseEntity<ResponseHolder<List<Permission>>> getAllPermissions(@RequestParam("page") int page, @RequestParam("size") int size) {
//        return ResponseBuilder.okResponseBuilder(permissionService.getAll()).build();
//    }

    @GetMapping("/list")
    public ResponseEntity<ResponseHolder<List<PermissionDto>>> getAllPermissions() {
        List<PermissionDto> permissionDtos = permissionService.getAll().stream().map(permissionConverter::convertToDto).collect(Collectors.toList());
        return ResponseBuilder.okResponseBuilder(permissionDtos).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHolder<PermissionDto>> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getOneById(id);
        PermissionDto permissionDto = permissionConverter.convertToDto(permission);
        return ResponseBuilder.okResponseBuilder(permissionDto).build();
    }

}
