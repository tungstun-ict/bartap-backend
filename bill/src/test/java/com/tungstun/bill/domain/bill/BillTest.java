package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {
    private static final Person BARTENDER = new Person(123L, 321L, "bartender");
    private static final Product PRODUCT = new Product(123L, 123L, "name", "brand", new Money(1.5d));

    private Bill bill;

    @BeforeEach
    void setUp() {
        bill = new Bill(123L, 456L, BARTENDER);
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
        assertEquals(PRODUCT.getId(), bill.getOrders().get(0).getProduct().getId());
        assertEquals(amount, bill.getOrders().get(0).getAmount());
        assertEquals(BARTENDER, bill.getOrders().get(0).getBartender());
    }

    @Test
    void removeNotExistingOrder_ReturnsFalse() {
        assertFalse(bill.removeOrder(123L));
    }

    @Test
    void removeOrder_ReturnsTrueAndRemovesOrder() throws IllegalAccessException {
        bill.addOrder(PRODUCT, 1, BARTENDER);
        Order order = bill.getOrders().get(0);
        FieldUtils.writeField(order, "id", 123L, true); // Done to simulate a id generation usually done at persistence
        Long orderId = order.getId();

        assertTrue(bill.removeOrder(orderId));
    }

    private static Stream<Arguments> totalPriceArgs() {
        return Stream.of(
                Arguments.of(1.5d, new Integer[]{1}),
                Arguments.of(3.0d, new Integer[]{1, 1}),
                Arguments.of(15.0d, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
                Arguments.of(10.5d, new Integer[]{5, 2}),
                Arguments.of(90.0d, new Integer[]{20, 20, 20})
        );
    }

    @ParameterizedTest
    @MethodSource("totalPriceArgs")
    void totalPrice(Double expectedValue, Integer[] addProductAmounts) {
        for (Integer integer : addProductAmounts) {
            bill.addOrder(PRODUCT, integer, BARTENDER);
        }

        assertEquals(expectedValue, bill.totalPrice());
    }
}
