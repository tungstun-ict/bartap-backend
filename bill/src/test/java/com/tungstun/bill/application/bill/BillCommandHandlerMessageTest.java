package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.CreateBill;
import com.tungstun.bill.application.bill.command.DeleteBill;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.common.test.MessageProducerTestBases;
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
class BillCommandHandlerMessageTest extends MessageProducerTestBases {
    private static final Long BAR_ID = 123L;

    @Autowired
    private BillCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Bill, Long> repository;
    @Autowired
    private JpaRepository<Person, Long> personRepository;

    private Person customer;

    @BeforeAll
    static void beforeAll() {
        topic = "bill";
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        customer = personRepository.save(new Person(456L, BAR_ID, "customer"));
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
        repository.deleteAll();
    }

    @Test
    void createBill_PublishesBillCreated() throws InterruptedException {
        Long id = commandHandler.handle(new CreateBill(123L, customer.getId(), 789L));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("BillCreated", singleRecord);
    }

    @Test
    void deleteBill_PublishesBillDeleted() throws InterruptedException {
        Bill bill = repository.save(new Bill(BAR_ID, 456L, customer));
        Long id = bill.getId();

        commandHandler.handle(new DeleteBill(id, bill.getBarId()));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("BillDeleted", singleRecord);
    }
}
