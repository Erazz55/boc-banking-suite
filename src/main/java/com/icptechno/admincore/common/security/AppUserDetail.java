package com.icptechno.admincore.common.security;

import com.icptechno.admincore.user.ApplicationUser;
import com.icptechno.admincore.user.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AppUserDetail implements UserDetails {

    Long id;
    String password;
    String email;
    UserStatus status;
    Set<PermissionAuthority> permissionAuthorities;

    public AppUserDetail(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.password = applicationUser.getPassword();
        this.email = applicationUser.getEmail();
        this.status = applicationUser.getStatus();
        this.permissionAuthorities = applicationUser.getRole().getPermissions().stream().map(p -> new PermissionAuthority(p.getName())).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissionAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return "AppUserDetail{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", permissionAuthorities=" + permissionAuthorities +
                '}';
    }
}
