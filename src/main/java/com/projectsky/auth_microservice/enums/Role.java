package com.projectsky.auth_microservice.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, PREMIUM_USER, GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
