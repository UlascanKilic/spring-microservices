package com.ulascan.apigateway.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    SERVER("SERVER"),
    INSTRUCTOR("INSTRUCTOR")

    ;

    @Getter
    private final String role;

    public static Role fromString(String authority) {
        for (Role role : Role.values()) {
            if (role.getRole().equals(authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid authority: " + authority);
    }

}
