package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {
    private static final Bartender BARTENDER = new Bartender(321L, "bartender");
    private static final Product PRODUCT = new Product("name", "brand", new Money(2d));

    private Bill bill;

    @BeforeEach
    void setUp() {
        bill = new Bill(123L, 456L, 789L);
    }

    @Test
    void newBill_ContainsNoOrders() {
        assertTrue(bill.getOrders().isEmpty());
    }

    @Test
    void addOrderWithoutProduct_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> bill.addOrder(null, 1, BARTENDER)
        );
    }

    @Test
    void addOrderWithoutBartender_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> bill.addOrder(PRODUCT, 1, null)
        );
    }

    @ParameterizedTest
    @CsvSource({"0", "-1", "-999"})
    void addOrderWithInvalidAmount_Throws(int amount) {
        assertThrows(
                IllegalArgumentException.class,
                () -> bill.addOrder(PRODUCT, amount, BARTENDER)
        );
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "999"})
    void addOrder_AddsOrder(int amount) {
        bill.addOrder(PRODUCT, amount, BARTENDER);

        assertEquals(1, bill.getOrders().size());
        assertEquals(PRODUCT, bill.getOrders().get(0).getProduct());
        assertEquals(amount, bill.getOrders().get(0).getAmount());
        assertEquals(BARTENDER, bill.getOrders().get(0).getBartender());
    }

    @Test
    void removeNotExistingOrder_ReturnsFalse() {
        assertFalse(bill.removeOrder(123L));
    }

    @Test
    void removeOrder_ReturnsTrueAndRemovesOrder() {
        bill.addOrder(PRODUCT, 1, BARTENDER);
        Long orderId = bill.getOrders().get(0).getId();

        assertTrue(bill.removeOrder(orderId));
    }
}
