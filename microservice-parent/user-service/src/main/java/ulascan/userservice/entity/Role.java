package ulascan.userservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ulascan.userservice.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    SERVER_READ,
                    SERVER_UPDATE,
                    SERVER_DELETE,
                    SERVER_CREATE,
                    INSTRUCTOR_READ,
                    INSTRUCTOR_UPDATE,
                    INSTRUCTOR_DELETE,
                    INSTRUCTOR_CREATE
            )
    ),
    SERVER(
            Set.of(
                    SERVER_READ,
                    SERVER_UPDATE,
                    SERVER_DELETE,
                    SERVER_CREATE
            )
    ),

    INSTRUCTOR(
            Set.of(
                    INSTRUCTOR_READ,
                    INSTRUCTOR_UPDATE,
                    INSTRUCTOR_DELETE,
                    INSTRUCTOR_CREATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
