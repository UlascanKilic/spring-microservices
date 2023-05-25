package com.ulascan.apigateway.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    SERVER("SERVER"),
    USER("USER"),
    TUTOR("TUTOR"),

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
