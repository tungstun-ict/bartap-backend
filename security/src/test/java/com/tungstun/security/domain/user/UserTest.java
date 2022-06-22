package com.tungstun.security.domain.user;

import com.tungstun.sharedlibrary.security.exception.NotAuthorizedException;
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
    private User userWithBarAuth;
    private User userWithoutBarAuth;

    @BeforeEach
    void setUp() {
        barId = 123L;
        userWithBarAuth = new User(
                "username",
                "password",
                "mail@mail.com",
                "first",
                "last",
                new ArrayList<>(List.of(new Authorization(barId, Role.OWNER))));
        userWithoutBarAuth = new User(
                "username",
                "password",
                "mail@mail.com",
                "first",
                "last",
                new ArrayList<>());
    }

    @Test
    void userWithAuthorization_ContainsAuthorization() {
        String actualRole = userWithBarAuth.getAuthorizations().get(barId);

        assertEquals(Role.OWNER.name(), actualRole);
    }

    @Test
    void userWithoutAuthorization_HasNoAuthorizations() {
        Map<Long, String> auths = userWithoutBarAuth.getAuthorizations();

        assertTrue(auths.isEmpty());
    }

    @Test
    void addNewBarAuthorization_Successfully() {
        Long newBarId = 987L;

        userWithoutBarAuth.newBarAuthorization(newBarId);

        assertTrue(userWithoutBarAuth.getAuthorizations().containsKey(newBarId));
        assertEquals(Role.OWNER.name(), userWithoutBarAuth.getAuthorizations().get(newBarId));
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
        assertDoesNotThrow(() -> userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, role));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"BARTENDER", "CUSTOMER"})
    void authorizeUserForOwnedBar_AuthorizesUserForBar(Role role) {
        userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, role);

        String userTwoRole = userWithoutBarAuth.getAuthorizations().get(barId);
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
        userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, role);

        assertDoesNotThrow(() -> userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, newRole));

        String user2Role = userWithoutBarAuth.getAuthorizations().get(barId);
        assertEquals(newRole.name(), user2Role);
    }

    @Test
    void authorizeUserForOwnedBarAsOwner_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, Role.OWNER)
        );
    }

    @Test
    void authorizeUserForNotOwnedBarWithNoOwnedBars_Throws() {
        assertThrows(
                NotAuthorizedException.class,
                () -> userWithoutBarAuth.authorizeUser(userWithBarAuth, barId, Role.CUSTOMER)
        );
    }

    @Test
    void authorizeUserForNotOwnedBar_Throws() {
        Long notOwnedBarId = 987L;

        assertThrows(
                NotAuthorizedException.class,
                () -> userWithBarAuth.authorizeUser(userWithoutBarAuth, notOwnedBarId, Role.CUSTOMER)
        );
    }

    @Test
    void authorizeUserForNotOwnedButRelatedBar_Throws() {
        userWithBarAuth.authorizeUser(userWithoutBarAuth, barId, Role.BARTENDER);

        assertThrows(
                NotAuthorizedException.class,
                () -> userWithoutBarAuth.authorizeUser(userWithBarAuth, barId, Role.CUSTOMER)
        );
    }

    @Test
    void userAuthorizesItself_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userWithBarAuth.authorizeUser(userWithBarAuth, barId, Role.CUSTOMER)
        );
    }
}