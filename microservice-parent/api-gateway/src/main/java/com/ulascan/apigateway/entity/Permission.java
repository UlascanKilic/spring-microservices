package com.ulascan.apigateway.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    SERVER_READ("server:read"),
    SERVER_UPDATE("server:update"),
    SERVER_CREATE("server:create"),
    SERVER_DELETE("server:delete"),
    INSTRUCTOR_READ("instructor:read"),
    INSTRUCTOR_UPDATE("instructor:update"),
    INSTRUCTOR_CREATE("instructor:create"),
    INSTRUCTOR_DELETE("instructor:delete")

    ;



    @Getter
    private final String permission;

    public static Permission fromString(String authority) {
        for (Permission permission : Permission.values()) {
            if (permission.getPermission().equals(authority)) {
                return permission;
            }
        }
        throw new IllegalArgumentException("Invalid authority: " + authority);
    }
}
