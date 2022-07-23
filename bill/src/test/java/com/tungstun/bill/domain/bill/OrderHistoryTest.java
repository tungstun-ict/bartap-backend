package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderHistoryTest {
    @Test
    void addHistoryEntry() {
        Long BAR_ID = 123L;
        Person customer = new Person(456L, BAR_ID, "customer");
        Person bartender = new Person(789L, BAR_ID, "bartender");
        Product product = new Product(123L, BAR_ID, "name", "brand", new Money(2.0d));
        int amount = 2;
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        Order order = new Order(product, amount, bartender);
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);
        OrderHistory history = new OrderHistory(new ArrayList<>());

        history.addEntry(order, customer);

        assertEquals(1, history.getHistory().size());
        OrderHistoryEntry entry = history.getHistory().get(0);
        assertEquals(customer, entry.getCustomer());
        assertEquals(bartender, entry.getBartender());
        assertEquals(amount, entry.getAmount());
        assertTrue(entry.getDate().isAfter(before)
                && entry.getDate().isBefore(after));
    }
}