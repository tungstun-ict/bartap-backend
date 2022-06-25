package com.tungstun.security.domain.user;

import com.tungstun.common.security.exception.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private Long barId;
    private User userWithAuthorization;
    private User user;

    @BeforeEach
    void setUp() {
        barId = 123L;
        userWithAuthorization = new User(
                "username",
                "password",
                "mail@mail.com",
                "first",
                "last",
                new ArrayList<>(List.of(new Authorization(barId, Role.OWNER))));
        user = new User(
                "username",
                "password",
                "mail@mail.com",
                "first",
                "last",
                new ArrayList<>());
    }

    @Test
    void userWithAuthorization_ContainsAuthorization() {
        String actualRole = userWithAuthorization.getAuthorizations().get(barId);

        assertEquals(Role.OWNER.name(), actualRole);
    }

    @Test
    void userWithoutAuthorization_HasNoAuthorizations() {
        Map<Long, String> auths = user.getAuthorizations();

        assertTrue(auths.isEmpty());
    }

    @Test
    void addNewBarAuthorization_Successfully() {
        Long newBarId = 987L;

        user.newBarAuthorization(newBarId);

        assertTrue(user.getAuthorizations().containsKey(newBarId));
        assertEquals(Role.OWNER.name(), user.getAuthorizations().get(newBarId));
    }

//  Commented due to not having functionality to check if other use already owns bar in Domain layer
//    @Test
//    void addNewBarAuthorization_WhenBarIsOwnedByOtherUser_Throws() {
//        Long barId = 123L;
//        User testUser = new User("username", "password", "mail@mail.com", "first", "last", new ArrayList<>());
//        testUser.newBarAuthorization(barId);
//
//        assertThrows(
//                SomeException.class,
//                () -> user.newBarAuthorization(barId)
//        );
//    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"BARTENDER", "CUSTOMER"})
    void authorizeUserForOwnedBar_DoesNotThrow(Role role) {
        assertDoesNotThrow(() -> userWithAuthorization.authorizeUser(user, barId, role));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"BARTENDER", "CUSTOMER"})
    void authorizeUserForOwnedBar_AuthorizesUserForBar(Role role) {
        userWithAuthorization.authorizeUser(user, barId, role);

        String userTwoRole = user.getAuthorizations().get(barId);
        assertEquals(role.name(), userTwoRole);
    }

    private static Stream<Arguments> provideRolesForChangingRoles() {
        return Stream.of(
                Arguments.of(Role.CUSTOMER, Role.BARTENDER),
                Arguments.of(Role.BARTENDER, Role.CUSTOMER)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRolesForChangingRoles")
    void updateAuthorizationOfUser_Successfully_AndDoesNotThrow(Role role, Role newRole) {
        userWithAuthorization.authorizeUser(user, barId, role);

        assertDoesNotThrow(() -> userWithAuthorization.authorizeUser(user, barId, newRole));

        String user2Role = user.getAuthorizations().get(barId);
        assertEquals(newRole.name(), user2Role);
    }

    @Test
    void authorizeUserForOwnedBarAsOwner_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userWithAuthorization.authorizeUser(user, barId, Role.OWNER)
        );
    }

    @Test
    void authorizeUserForNotOwnedBarWithNoOwnedBars_Throws() {
        assertThrows(
                NotAuthorizedException.class,
                () -> user.authorizeUser(userWithAuthorization, barId, Role.CUSTOMER)
        );
    }

    @Test
    void authorizeUserForNotOwnedBar_Throws() {
        Long notOwnedBarId = 987L;

        assertThrows(
                NotAuthorizedException.class,
                () -> userWithAuthorization.authorizeUser(user, notOwnedBarId, Role.CUSTOMER)
        );
    }

    @Test
    void authorizeUserForNotOwnedButRelatedBar_Throws() {
        userWithAuthorization.authorizeUser(user, barId, Role.BARTENDER);

        assertThrows(
                NotAuthorizedException.class,
                () -> user.authorizeUser(userWithAuthorization, barId, Role.CUSTOMER)
        );
    }

    @Test
    void userAuthorizesItself_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userWithAuthorization.authorizeUser(userWithAuthorization, barId, Role.CUSTOMER)
        );
    }

    @Test
    void revokeAuthorizedUserAuthorization_RevokesAuthorization() {
        userWithAuthorization.authorizeUser(user, barId, Role.CUSTOMER);

        userWithAuthorization.revokeUserAuthorization(user, barId);

        assertNull(user.getAuthorizations().get(barId));
    }

    @Test
    void revokeUserAuthorizationWithNoAuthorization_Throws() {
        assertThrows(
                NotAuthorizedException.class,
                () -> user.revokeUserAuthorization(userWithAuthorization, barId)
        );
    }

    @Test
    void revokeUserAuthorizationWithNoAuthorizationLevel_Throws() {
        userWithAuthorization.authorizeUser(user, barId, Role.CUSTOMER);

        assertThrows(
                NotAuthorizedException.class,
                () -> user.revokeUserAuthorization(userWithAuthorization, barId)
        );
    }

    @Test
    void revokeOwnUserAuthorization_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userWithAuthorization.revokeUserAuthorization(userWithAuthorization, barId)
        );
    }

    @Test
    void revokeOwnership_Successfully() {
        assertDoesNotThrow(() -> userWithAuthorization.revokeOwnership(barId));
    }

    @Test
    void revokeOwnershipOfNotOwnedBar_Throws() {
        assertThrows(
                NotAuthorizedException.class,
                () -> user.revokeOwnership(barId)
        );
    }
}