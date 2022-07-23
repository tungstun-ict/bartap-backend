package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {
    private static final Long BAR_ID = 123L;
    private static final Person BARTENDER = new Person(789L, BAR_ID, "bartender");

    private static Stream<Arguments> orderPricesTestArgs() {
        return Stream.of(
                Arguments.of(
                        2.0d,
                        1,
                        new Product(123L, BAR_ID, "name", "brand", new Money(2.0d))
                ),
                Arguments.of(
                        4.0d,
                        2,
                        new Product(123L, BAR_ID, "name", "brand", new Money(2.0d))
                ),
                Arguments.of(
                        0.0d,
                        1,
                        new Product(123L, BAR_ID, "name", "brand", new Money(0.0d))
                ),
                Arguments.of(
                        0.0d,
                        2,
                        new Product(123L, BAR_ID, "name", "brand", new Money(0.0d))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("orderPricesTestArgs")
    void orderPrice(Double expectedValue, Integer amountOfProducts, Product product) {
        Order order = new Order(product, amountOfProducts, BARTENDER);

        assertEquals(expectedValue, order.orderPrice());
    }
}