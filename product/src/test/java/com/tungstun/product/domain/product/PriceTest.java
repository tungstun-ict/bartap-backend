package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @Test
    void constructPrice_CreatesPricesWithMoneyAndCurrentDate() {
        Price price = new Price(new Money(2.5d));

        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        LocalDateTime priceFromDate = price.getFromDate().truncatedTo(ChronoUnit.MICROS);
        assertTrue(priceFromDate.isEqual(localDateTime));
        assertNull(price.getToDate());
    }

    @Test
    void newPrice_IsActive() {
        Price price = new Price(new Money(2.5d));

        assertTrue(price.isActive());
    }

    @Test
    void endPrice_MakesPriceNotActive() {
        Price price = new Price(new Money(2.5d));
        price.endPricing();

        assertFalse(price.isActive());
        assertNotNull(price.getToDate());
    }
}