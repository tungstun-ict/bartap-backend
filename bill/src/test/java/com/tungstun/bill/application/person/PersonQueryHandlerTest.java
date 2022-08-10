package com.tungstun.bill.application.person;

import com.tungstun.bill.application.person.query.GetPerson;
import com.tungstun.bill.domain.person.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class PersonQueryHandlerTest {
    @Autowired
    private PersonQueryHandler queryHandler;
    @Autowired
    private JpaRepository<Person, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getPerson() {
        Person person = repository.save(new Person(
                123L,
                456L,
                "username"
        ));

        Person retrievedPerson = queryHandler.handle(new GetPerson(person.getId()));

        assertEquals(person.getBarId(), retrievedPerson.getBarId());
        assertEquals(person.getUsername(), retrievedPerson.getUsername());
    }
}
