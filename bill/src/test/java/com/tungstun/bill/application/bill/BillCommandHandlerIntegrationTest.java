package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.CreateBill;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.common.test.EmbeddedKafkaTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BillCommandHandlerIntegrationTest extends EmbeddedKafkaTestBase {
    private static final Long BAR_ID = 789L;

    @Autowired
    private BillCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Bill, Long> repository;
    @Autowired
    private JpaRepository<Person, Long> personRepository;

    private Person person;

    @BeforeEach
    void setUp() {
        person = personRepository.save(new Person(456L, BAR_ID, "usernamer"));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createBill_CreatesBill() {
        CreateBill command = new CreateBill(123L, person.getId(), 789L);

        Long billId = commandHandler.handle(command);

        assertTrue(repository.findById(billId).isPresent());
    }

    @Test
    void createBillWhenCustomerAlreadyHasBillInSession_Throws() {
        CreateBill command = new CreateBill(123L, person.getId(), BAR_ID);
        commandHandler.handle(command);

        assertThrows(
                IllegalArgumentException.class,
                () -> commandHandler.handle(command)
        );
    }
}