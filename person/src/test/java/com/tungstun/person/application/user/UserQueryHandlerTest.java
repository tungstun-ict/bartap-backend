package com.tungstun.person.application.user;

import com.tungstun.person.application.user.query.GetUser;
import com.tungstun.person.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserQueryHandlerTest {
    @Autowired
    private UserQueryHandler commandHandler;
    @Autowired
    private JpaRepository<User, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getUser_Successfully() {
        User user = repository.save(new User(
                123L,
                "username",
                "+31687654321",
                "Doe John"
        ));
        GetUser command = new GetUser(user.getId());

        User retrievedUser = commandHandler.handle(command);

        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPhoneNumber(), retrievedUser.getPhoneNumber());
        assertEquals(user.getFullName(), retrievedUser.getFullName());
    }

    @Test
    void getUserWithWrongId_Throws() {
        GetUser command = new GetUser(999L);

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }
}
