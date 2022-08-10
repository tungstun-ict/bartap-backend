package com.tungstun.bill.application.person;

import com.tungstun.bill.application.person.command.CreatePerson;
import com.tungstun.bill.application.person.command.DeletePerson;
import com.tungstun.bill.application.person.command.UpdatePerson;
import com.tungstun.bill.domain.person.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PersonCommandHandlerTest {
    @Autowired
    private PersonCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Person, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createPerson() {
        CreatePerson command = new CreatePerson(
                123L,
                321L,
                "username"
        );

        commandHandler.handle(command);

        assertTrue(repository.findById(command.id()).isPresent());
    }

    @Test
    void updatePerson() {
        Long id = repository.save(new Person(
                123L,
                456L,
                "username"
        )).getId();
        UpdatePerson command = new UpdatePerson(id, "newUsername");

        commandHandler.handle(command);

        Person person = repository.findById(id).orElseThrow();
        assertEquals(command.username(), person.getUsername());
    }

    @Test
    void deletePerson() {
        Long id = repository.save(new Person(
                123L,
                456L,
                "username"
        )).getId();
        DeletePerson command = new DeletePerson(id);

        commandHandler.handle(command);
        assertTrue(repository.findById(id).isEmpty());
    }
}
