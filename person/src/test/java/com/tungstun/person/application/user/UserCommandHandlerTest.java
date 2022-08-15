package com.tungstun.person.application.user;

import com.tungstun.person.application.user.command.CreateUser;
import com.tungstun.person.application.user.command.DeleteUser;
import com.tungstun.person.application.user.command.UpdateUser;
import com.tungstun.person.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserCommandHandlerTest {
    @Autowired
    private UserCommandHandler commandHandler;
    @Autowired
    private JpaRepository<User, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    private static Stream<Arguments> createUserArgs() {
        return Stream.of(
                Arguments.of("+31612345678", "John Doe"),
                Arguments.of(null, "John Doe"),
                Arguments.of("+31612345678", null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("createUserArgs")
    void createUserWithCorrectArgumentCombinations(String phoneNumber, String fullName) {
        CreateUser command = new CreateUser(
                123L,
                "username",
                phoneNumber,
                fullName
        );

        commandHandler.handle(command);

        assertTrue(repository.findById(command.id()).isPresent());
    }

    @ParameterizedTest
    @MethodSource("createUserArgs")
    void updateUser(String phoneNumber, String fullName) {
        Long id = repository.save(new User(
                123L,
                "username",
                "+31687654321",
                "Doe John"
        )).getId();
        UpdateUser command = new UpdateUser(id, "newUsername", phoneNumber, fullName);

        commandHandler.handle(command);

        User user = repository.findById(id).orElseThrow();
        String expectedPhoneNumberString = user.getPhoneNumber() != null
                ? user.getPhoneNumber().getValue()
                : null;
        assertEquals(command.phoneNumber(), expectedPhoneNumberString);
        assertEquals(command.fullName(), user.getFullName());
    }

    @Test
    void deletePerson() {
        Long id = repository.save(new User(
                123L,
                "username",
                "+31687654321",
                "Doe John"
        )).getId();
        DeleteUser command = new DeleteUser(id);

        commandHandler.handle(command);

        assertTrue(repository.findById(id).isEmpty());
    }
}
