package com.icptechno.admincore.permission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByName(String name);

    List<Permission> findPermissionsByIdIn(List<Long> ids);

}
