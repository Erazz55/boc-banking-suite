package com.icptechno.admincore.permission;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

//    public List<Permission> getPaginated(int size, int page) {
//        return permissionRepository.findAll(PageRequest.of(size, page)).toList();
//    }

    public Permission getOneById(Long id) {
        return permissionRepository.findById(id).orElseThrow();
    }

}
