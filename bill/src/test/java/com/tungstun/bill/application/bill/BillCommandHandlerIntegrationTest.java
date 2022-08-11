package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.*;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import com.tungstun.common.test.EmbeddedKafkaTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BillCommandHandlerIntegrationTest extends EmbeddedKafkaTestBase {
    private static final Long BAR_ID = 123L;

    @Autowired
    private BillCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Bill, Long> repository;
    @Autowired
    private JpaRepository<Person, Long> personRepository;
    @Autowired
    private JpaRepository<Product, Long> productRepository;

    private Bill bill;
    private Person person;
    private Product product;

    @BeforeEach
    void setUp() {
        person = personRepository.save(new Person(
                456L,
                BAR_ID,
                "username"));
        bill = repository.save(new Bill(
                BAR_ID,
                456L,
                person));
        product = productRepository.save(new Product(
                123L,
                456L,
                "name",
                "brand",
                new Money(1)));
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
        CreateBill command = new CreateBill(bill.getSessionId(), person.getId(), BAR_ID);

        assertThrows(
                IllegalArgumentException.class,
                () -> commandHandler.handle(command)
        );
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void updateBillPayedTrue_UpdatesIsPayedToTrue(boolean isPayed) {
        UpdateBillPayed command = new UpdateBillPayed(bill.getId(), bill.getBarId(), isPayed);

        Long billId = commandHandler.handle(command);

        Bill b = repository.findById(billId).orElseThrow();
        assertEquals(isPayed, b.isPayed());
    }


    @Test
    void updateBillPayedWithWrongBarId_Throws() {
        UpdateBillPayed command = new UpdateBillPayed(bill.getId(), 999L, true);

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void updateBillPayedWithWrongBillId_Throws() {
        UpdateBillPayed command = new UpdateBillPayed(999L, BAR_ID, true);

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void deleteBill_DeletesBill() {
        DeleteBill command = new DeleteBill(bill.getId(), bill.getBarId());

        commandHandler.handle(command);

        assertTrue(repository.findById(bill.getId()).isEmpty());
    }

    @Test
    void deleteBillWithWrongBarId_Throws() {
        DeleteBill command = new DeleteBill(bill.getId(), 999L);

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void deleteBillWithWrongBillId_Throws() {
        DeleteBill command = new DeleteBill(999L, bill.getBarId());

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void addOrderToBillWithSecondBartender_Successfully() {
        Person bartender = personRepository.save(new Person(
                789L,
                BAR_ID,
                "bartender"));
        AddOrder command = new AddOrder(
                bill.getId(),
                BAR_ID,
                bartender.getId(),
                product.getId(),
                1
        );

        commandHandler.handle(command);

        bill = repository.getById(bill.getId());
        assertEquals(1, bill.getOrders().size());
    }

    @Test
    void addOrderToBillAsBartender_Successfully() {
        AddOrder command = new AddOrder(
                bill.getId(),
                BAR_ID,
                person.getId(),
                product.getId(),
                1
        );

        commandHandler.handle(command);

        int actualAmount = repository.getById(bill.getId()).getOrders().size();
        assertEquals(command.amount(), actualAmount);
    }

    @Test
    void addOrderWithWrongProductId_Throws() {
        AddOrder command = new AddOrder(
                bill.getId(),
                BAR_ID,
                person.getId(),
                999L,
                1
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void addOrderWithWrongPersonId_Throws() {
        AddOrder command = new AddOrder(
                bill.getId(),
                BAR_ID,
                999L,
                product.getId(),
                1
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> commandHandler.handle(command)
        );
    }

    @Test
    void removeOrderFromBill_Successfully() {
        bill.addOrder(product, 1, person);
        Long orderId = repository.save(bill)
                .getOrders()
                .get(0)
                .getId();
        RemoveOrder command = new RemoveOrder(
                bill.getId(),
                BAR_ID,
                orderId,
                product.getId()
        );

        commandHandler.handle(command);

        int actualAmount = repository.getById(bill.getId()).getOrders().size();
        assertEquals(0, actualAmount);
    }
}