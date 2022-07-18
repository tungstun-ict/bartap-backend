package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.util.FieldUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PricesTest {
    private Money money;
    private Prices prices;

    @BeforeEach
    void setUp() {
        money = new Money(2.5d);
        prices = new Prices(money);
    }

    @Test
    void constructPrices_CreatesPrices_WithInitialPrice() {
        List<Price> allPrices = (List<Price>) FieldUtils.getProtectedFieldValue("allPrices", prices);

        assertEquals(1, allPrices.size());
        assertEquals(money, allPrices.get(0).getMoney());
    }

    @Test
    void updatePrice_ChangesCurrentPrice() {
        assertEquals(money, prices.currentPrice().getMoney());
        Money newMoney = new Money(2.0d);

        prices.updatePrice(newMoney);

        assertEquals(newMoney, prices.currentPrice().getMoney());
    }

    @Test
    void updatePriceIsNull_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> prices.updatePrice(null)
        );
    }

    @Test
    void currentPrice_ReturnsTheActivePrice() {
        assertEquals(money, prices.currentPrice().getMoney());
    }

    @Test
    void currentPriceWithMultiplePrices_ReturnsTheActivePrice() {
        Money money2 = new Money(3d);
        Money money3 = new Money(4d);
        prices.updatePrice(money2);
        prices.updatePrice(money3);

        Price currentPrice = prices.currentPrice();

        assertEquals(money3, currentPrice.getMoney());
    }

    @Test
    void currentPriceWithTwoPrices_ReturnsTheActivePrice() {
        Money newMoney = new Money(2.0d);

        prices.updatePrice(newMoney);

        assertEquals(newMoney, prices.currentPrice().getMoney());
    }

    @Test
    void noCurrentPrice_Throws() {
        List<Price> allPrices = (List<Price>) FieldUtils.getProtectedFieldValue("allPrices", prices);
        allPrices.get(0).endPricing();

        assertThrows(
                IllegalArgumentException.class,
                prices::currentPrice
        );
    }

    @Test
    void priceAtDateWithOnePrice_EqualsCurrent() {
        Price currentPrice = prices.currentPrice();
        Price atDatePrice = prices.atDate(LocalDateTime.now().plusSeconds(5));

        assertEquals(currentPrice, atDatePrice);
    }

    @Test
    void priceAtDateAfterNowWithMultiplePrices_EqualsCurrent() {
        Money money2 = new Money(3d);
        Money money3 = new Money(4d);
        prices.updatePrice(money2);
        prices.updatePrice(money3);

        Price atDatePrice = prices.atDate(LocalDateTime.now().plusSeconds(1));

        assertEquals(money3, atDatePrice.getMoney());
    }

    @SuppressWarnings("java:S2925")
    @Test
    void priceAtPreviousDateWithMultiplePrices_EqualsCurrent() throws InterruptedException {
        Thread.sleep(10); // Sleep thread to make sure localDateTime is after creation date of first price
        LocalDateTime localDateTime = LocalDateTime.now();
        Thread.sleep(10); // Sleep thread to make sure localDateTime is before creation date of second price
        Money money2 = new Money(3d);
        prices.updatePrice(money2);

        Price atDatePrice = prices.atDate(localDateTime);

        assertEquals(money, atDatePrice.getMoney());
    }

    @Test
    void priceAtDateBeforeInitialPriceDate_Throws() {
        LocalDateTime localDateTime = LocalDateTime.now().minusSeconds(5);

        assertThrows(
                IllegalArgumentException.class,
                () -> prices.atDate(localDateTime)
        );
    }
}