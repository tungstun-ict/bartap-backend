package com.tungstun.security.domain.user;

import java.util.Locale;

public enum Role {
    OWNER,
    BARTENDER,
    CUSTOMER;

    public static Role getRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("'%s' is not an existing role type", role), e);
        }
    }
}
