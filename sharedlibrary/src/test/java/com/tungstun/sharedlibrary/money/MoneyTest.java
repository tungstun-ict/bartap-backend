package com.tungstun.sharedlibrary.money;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void newMoneyObjectAmount_IsTwoPlacesBehindDecimal() {
        Money money = new Money(12.3456789);
        assertEquals(0, money.amount().compareTo(BigDecimal.valueOf(12.35)));
    }

    @Test
    void simpleConstructor_CreatesEuroMoneyObject() {
        double amount = 12.34;
        Money money = new Money(amount);

        assertEquals("EUR", money.currency().code());
        assertEquals(BigDecimal.valueOf(amount), money.amount());
    }

    @Test
    void constructor_ExpectRoundDownToTwoDecimalPlaces() {
        BigDecimal expectedAmount = BigDecimal.valueOf(12.00)
                .setScale(2, RoundingMode.UNNECESSARY);
        double amount = 12;
        Money money = new Money(amount);

        assertEquals(expectedAmount, money.amount());
    }

    @Test
    void constructor_ExpectRoundUpToTwoDecimalPlaces() {
        BigDecimal expectedAmount = BigDecimal.valueOf(12.35)
                .setScale(2, RoundingMode.UNNECESSARY);
        double amount = 12.3456789;
        Money money = new Money(amount);

        assertEquals(expectedAmount, money.amount());
    }

    @ParameterizedTest
    @CsvSource({
            "11.995",
            "12",
            "12.0",
            "12.00",
            "12.001"
    })
    void sameAmountSameCurrency_ComparesSame(double amount) {
        Money money = new Money(12);
        Money money2 = new Money(amount);

        assertEquals(0, money.compareTo(money2));
    }

    @ParameterizedTest
    @CsvSource({
            "12.005",
            "12.01",
            "12.04"
    })
    void smallerAmountSameCurrency_ComparesToMinusOne(double amount) {
        Money money = new Money(12.0);
        Money money2 = new Money(amount);

        assertEquals(-1, money.compareTo(money2));
    }
    @ParameterizedTest
    @CsvSource({
            "11.99",
            "11.994"
    })
    void higherAmountSameCurrency_ComparesToOne(double amount) {
        Money money = new Money(12.0);
        Money money2 = new Money(amount);

        assertEquals(1, money.compareTo(money2));
    }

    @Test
    void toString_ReturnsCharacterAndValue() {
        double amount = 12.34;
        Money money = new Money(amount);

        String expected = "€ " + amount;
        assertEquals(expected, money.toString());
    }

    @Test
    void increaseMoney_ReturnsNewMoney() {
        Money money = new Money(5);
        Money increaseMoney = money.increase(5);

        assertEquals(BigDecimal.valueOf(10.0), increaseMoney.amount());
    }
    @Test
    void decreaseMoney_ReturnsNewMoney() {
        Money money = new Money(10);
        Money increaseMoney = money.decrease(5);

        assertEquals(BigDecimal.valueOf(5.0), increaseMoney.amount());
    }
}