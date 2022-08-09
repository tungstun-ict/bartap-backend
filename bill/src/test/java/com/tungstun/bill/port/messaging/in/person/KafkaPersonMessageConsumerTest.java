package com.tungstun.bill.port.messaging.in.person;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.port.messaging.in.person.message.PersonCreated;
import com.tungstun.bill.port.messaging.in.person.message.PersonDeleted;
import com.tungstun.bill.port.messaging.in.person.message.PersonUpdated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KafkaPersonMessageConsumerTest {
    @Autowired
    private KafkaPersonMessageConsumer consumer;
    @Autowired
    private JpaRepository<Person, Long> repository;

    @Test
    void personCreated() {
        PersonCreated event = new PersonCreated(
                123L,
                321L,
                "username"
        );
        consumer.handlePersonCreated(event);

        assertTrue(repository.findById(event.id()).isPresent());
    }

    @Test
    void personUpdated() {
        Long id = repository.save(new Person(
                123L,
                456L,
                "username"
        )).getId();
        PersonUpdated event = new PersonUpdated(id, "newUsername");

        consumer.handlePersonUpdated(event);

        Person person = repository.findById(id).orElseThrow();
        assertEquals(event.username(), person.getUsername());
    }

    @Test
    void personDeleted() {
        Long id = repository.save(new Person(
                123L,
                456L,
                "username"
        )).getId();
        PersonDeleted event = new PersonDeleted(id);

        consumer.handlePersonDeleted(event);
        assertTrue(repository.findById(id).isEmpty());
    }
}
