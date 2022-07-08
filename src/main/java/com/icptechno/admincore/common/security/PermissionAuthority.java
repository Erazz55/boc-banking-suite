package com.icptechno.admincore.common.security;

import org.springframework.security.core.GrantedAuthority;

public class PermissionAuthority implements GrantedAuthority {

    private String value;

    public PermissionAuthority(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }

    @Override
    public String toString() {
        return "PermissionAuthority{" +
                "value='" + value + '\'' +
                '}';
    }
}
