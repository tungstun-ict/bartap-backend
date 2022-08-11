package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.query.*;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.Order;
import com.tungstun.bill.domain.bill.OrderHistoryEntry;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BillQueryHandlerIntegrationTest {
    private static final Long BAR_ID = 123L;
    private static final Long SESSION_ID = 123L;

    @Autowired
    private BillQueryHandler queryHandler;
    @Autowired
    private JpaRepository<Bill, Long> repository;
    @Autowired
    private JpaRepository<Person, Long> personRepository;
    @Autowired
    private JpaRepository<Product, Long> productRepository;

    private Bill bill;
    private Bill billWithOrder;
    private Person person;
    private Person person2;
    private Product product;

    @BeforeEach
    void setUp() {
        person = personRepository.save(new Person(
                123L,
                BAR_ID,
                "username"));
        person2 = personRepository.save(new Person(
                456L,
                BAR_ID,
                "username"));
        product = productRepository.save(new Product(
                123L,
                456L,
                "name",
                "brand",
                new Money(1)));
        bill = repository.save(new Bill(BAR_ID, SESSION_ID, person));
        billWithOrder = new Bill(BAR_ID, SESSION_ID, person2);
        billWithOrder.addOrder(product, 1, person2);
        billWithOrder = repository.save(billWithOrder);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getBill_Successfully() {
        GetBill query = new GetBill(bill.getId(), BAR_ID);

        assertDoesNotThrow(() -> queryHandler.handle(query));
    }

    @Test
    void getBillWithWrongId_Throws() {
        GetBill query = new GetBill(999L, BAR_ID);

        assertThrows(
                EntityNotFoundException.class,
                () -> queryHandler.handle(query)
        );
    }

    @Test
    void getBillsOfSession_Successfully() {
        ListBillsOfSession query = new ListBillsOfSession(SESSION_ID, BAR_ID);

        List<Bill> bills = queryHandler.handle(query);
        assertEquals(2, bills.size());
    }


    @Test
    void getBillWithWrongSessionId_IsEmpty() {
        ListBillsOfSession query = new ListBillsOfSession(999L, BAR_ID);

        List<Bill> bills = queryHandler.handle(query);

        assertTrue(bills.isEmpty());
    }

    @Test
    void getBillsOfPerson_Successfully() {
        ListBillsOfPerson query = new ListBillsOfPerson(person2.getId(), BAR_ID);

        List<Bill> bills = queryHandler.handle(query);

        assertEquals(1, bills.size());
    }


    @Test
    void getBillWithWrongPersonId_IsEmpty() {
        ListBillsOfPerson query = new ListBillsOfPerson(999L, BAR_ID);

        List<Bill> bills = queryHandler.handle(query);

        assertTrue(bills.isEmpty());
    }

    @Test
    void getBillOrdersWhenOrderExists_Successfully() {
        ListOrdersOfBill query = new ListOrdersOfBill(billWithOrder.getId(), BAR_ID);

        List<Order> orders = queryHandler.handle(query);

        assertEquals(1, orders.size());
    }

    @Test
    void getBillOrdersWhenNoOrderExists_isEmpty() {
        ListOrdersOfBill query = new ListOrdersOfBill(bill.getId(), BAR_ID);

        List<Order> orders = queryHandler.handle(query);

        assertTrue(orders.isEmpty());
    }

    @Test
    void getBillOrderHistory_Successfully() {
        ListOrderHistory query = new ListOrderHistory(billWithOrder.getId(), BAR_ID);

        List<OrderHistoryEntry> orders = queryHandler.handle(query);

        assertEquals(1, orders.size());
    }

    @Test
    void getBillOrderHistoryWithNoOrders_isEmpty() {
        ListOrderHistory query = new ListOrderHistory(bill.getId(), BAR_ID);

        List<OrderHistoryEntry> orders = queryHandler.handle(query);

        assertTrue(orders.isEmpty());
    }

    @Test
    void getSessionOrderHistory_Successfully() {
        bill.addOrder(product, 1, person);
        repository.save(bill);
        ListSessionOrderHistory query = new ListSessionOrderHistory(SESSION_ID, BAR_ID);

        List<OrderHistoryEntry> orders = queryHandler.handle(query);

        assertEquals(2, orders.size());
    }
}
