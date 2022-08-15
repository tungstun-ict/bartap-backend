package com.tungstun.person.application.person;

import com.tungstun.common.test.MessageProducerTestBases;
import com.tungstun.person.application.person.command.CreatePerson;
import com.tungstun.person.application.person.command.DeletePerson;
import com.tungstun.person.application.person.command.UpdatePerson;
import com.tungstun.person.domain.person.Person;
import com.tungstun.person.domain.user.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class PersonCommandHandlerProducerTest extends MessageProducerTestBases {
    private static final Long BAR_ID = 123L;

    @Autowired
    private PersonCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Person, Long> repository;
    @Autowired
    private JpaRepository<User, Long> userRepository;

    private User user;
    private Person person;

    @BeforeAll
    static void beforeAll() {
        topic = "person";
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        user = userRepository.save(new User(123L, "username", "+31612345678", "John Doe"));
        person = repository.save(new Person(BAR_ID, "username", user));
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createPerson_PublishesPersonCreated() throws InterruptedException {
        CreatePerson command = new CreatePerson(BAR_ID, "newPerson", user.getId());

        Long id = commandHandler.handle(command);

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("PersonCreated", singleRecord);
    }

    @Test
    void createPersonWithoutUser_PublishesPersonCreated() throws InterruptedException {
        CreatePerson command = new CreatePerson(BAR_ID, "newPerson", null);

        Long id = commandHandler.handle(command);

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("PersonCreated", singleRecord);
    }

    @Test
    void updatePerson_PublishesPersonUpdated() throws InterruptedException {
        UpdatePerson command = new UpdatePerson(person.getId(), BAR_ID, "newPerson");

        Long id = commandHandler.handle(command);

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("PersonUpdated", singleRecord);
    }

    @Test
    void deletePerson_PublishesPersonDeleted() throws InterruptedException {
        DeletePerson command = new DeletePerson(person.getId(), BAR_ID);

        commandHandler.handle(command);

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(command.personId(), singleRecord);
        assertEventType("PersonDeleted", singleRecord);
    }
}