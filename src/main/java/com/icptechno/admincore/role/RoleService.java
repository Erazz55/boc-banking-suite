package com.icptechno.admincore.role;

import com.icptechno.admincore.common.ResourceStatus;
import com.icptechno.admincore.permission.Permission;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class RoleService {

    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getOneById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    public Role create(String name, Set<Permission> permissions) {
        Role role = new Role();
        role.setName(name);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public void updateById(Long id, String name, ResourceStatus status, Set<Permission> permissions) {
        Role role = getOneById(id);

        // update role
        role.setName(name);
        role.setStatus(status);
        role.setPermissions(permissions);

        roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
