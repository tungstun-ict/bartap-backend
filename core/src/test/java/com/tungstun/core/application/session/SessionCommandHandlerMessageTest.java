package com.tungstun.core.application.session;

import com.tungstun.common.test.MessageProducerTestBases;
import com.tungstun.core.application.session.command.CreateSession;
import com.tungstun.core.application.session.command.DeleteSession;
import com.tungstun.core.application.session.command.EndSession;
import com.tungstun.core.application.session.command.LockSession;
import com.tungstun.core.domain.session.Session;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SessionCommandHandlerMessageTest extends MessageProducerTestBases {
    @Autowired
    private SessionCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Session, Long> repository;

    private Session session;

    @BeforeAll
    static void beforeAll() {
        topic = "core";
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        session = repository.save(Session.with(123L, "session"));
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
        repository.deleteAll();
    }

    @Test
    void createSession_PublishesSessionCreated() throws InterruptedException {
        Long id = commandHandler.handle(new CreateSession(123L, "session2"));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("SessionCreated", singleRecord);
    }

    @Test
    void deleteSession_PublishesSessionDeleted() throws InterruptedException {
        Long id = session.getId();

        commandHandler.handle(new DeleteSession(id));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("SessionDeleted", singleRecord);
    }

    @Test
    void endSession_PublishesSessionEnded() throws InterruptedException {
        Long id = session.getId();

        commandHandler.handle(new EndSession(id));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("SessionEnded", singleRecord);
    }

    @Test
    void lockSession_PublishesSessionLocked() throws InterruptedException {
        session.endSession();
        Long id = repository.save(session).getId();

        commandHandler.handle(new LockSession(id));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("SessionLocked", singleRecord);
    }
}