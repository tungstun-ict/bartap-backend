package com.tungstun.security.domain.user;

public enum Role {
    OWNER,
    BARTENDER,
    CUSTOMER;

    public static Role getRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("'%s' is not an existing role type", role), e);
        }
    }

}
