package com.tungstun.security.domain.user;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @ParameterizedTest
    @CsvSource(value = {
            "OWNER",
            "owner",
            "BARTENDER",
            "bartender",
            "CUSTOMER", "customer"})
    void getRoleFromExistingRole_DoesNotThrow(String role) {
        assertDoesNotThrow(() -> Role.getRole(role));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "OWNE",
            "owne",
            "own er",
            "Family Guy",})
    void getRoleFromNotExistingRole_ThrowsException(String role) {
        assertThrows(
                IllegalArgumentException.class,
                () -> Role.getRole(role)
        );
    }
}