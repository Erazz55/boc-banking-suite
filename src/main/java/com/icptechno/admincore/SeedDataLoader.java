package com.icptechno.admincore;

import com.icptechno.admincore.common.ResourceStatus;
import com.icptechno.admincore.permission.Permission;
import com.icptechno.admincore.permission.PermissionRepository;
import com.icptechno.admincore.role.Role;
import com.icptechno.admincore.role.RoleRepository;
import com.icptechno.admincore.user.ApplicationUser;
import com.icptechno.admincore.user.UserRepository;
import com.icptechno.admincore.user.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SeedDataLoader implements CommandLineRunner {

//    Logger logger = LoggerFactory.getLogger(SeedDataLoader.class);

    final PermissionRepository permissionRepository;
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SeedDataLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadPermissionData();
        loadRoleData();
        loadUserData();
    }

    private void loadPermissionData() {
        if (permissionRepository.count() != 0) return;
        Stream.of(
                "USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE",
                "APPROVAL_READ", "APPROVAL_UPDATE",
                "USER_PROFILE_EDIT",
                "ROLE_CREATE", "ROLE_EDIT", "ROLE_DELETE"
        ).map(p -> new Permission(null, p, p, ResourceStatus.ACTIVE)).forEach(permissionRepository::save);
    }

    private void loadRoleData() {
        if (roleRepository.count() != 0) return;

        Set<String> superAdminPermissions = Stream.of("USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE", "APPROVAL_READ", "APPROVAL_UPDATE",
                "USER_PROFILE_EDIT",
                "ROLE_CREATE", "ROLE_EDIT", "ROLE_DELETE").collect(Collectors.toSet());
        createRole("SUPER_ADMIN", superAdminPermissions);

        Set<String> adminPermissions = Stream.of("USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE", "APPROVAL_READ",
                "USER_PROFILE_EDIT",
                "ROLE_CREATE", "ROLE_EDIT", "ROLE_DELETE").collect(Collectors.toSet());
        createRole("ADMIN", adminPermissions);

        Set<String> userPermissions = Stream.of("USER_READ", "USER_PROFILE_EDIT").collect(Collectors.toSet());
        createRole("USER", userPermissions);
    }

    private void createRole(String name, Set<String> permissionNames) {
        Set<Permission> permissions = permissionNames.stream().map(permissionRepository::findByName).collect(Collectors.toSet());
        Role role = new Role(null, name, ResourceStatus.ACTIVE, permissions);
        roleRepository.save(role);
    }

    private void loadUserData() {
        if (userRepository.count() != 0) return;
        Role superAdmin = new Role();
        superAdmin.setId(1L);
        Role user = new Role();
        user.setId(3L);
        List<ApplicationUser> userList = Stream.of(
                new ApplicationUser(1L, "ICP", "Techno", "icp@demo.com", "icppwd1#", "0717127121", superAdmin, UserStatus.ACTIVE),
                new ApplicationUser(2L, "Admin2", "Admin2", "admin2@demo.com", "adminpwd2", "0717127122", user, UserStatus.ACTIVE)
        ).peek(u -> u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()))).collect(Collectors.toList());
        userList.forEach(userRepository::save);
    }
}
