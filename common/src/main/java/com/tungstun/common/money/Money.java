package com.tungstun.common.money;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value object to be used for monetary value.
 */
@Embeddable
public class Money implements Comparable<Money> {
    private static final Currency defaultCurrency = new Currency("€", "EUR");

    @Column(name = "money_amount")
    private BigDecimal amount;

    private Currency currency;

    public Money() {
    }

    public Money(double amount) {
        this(BigDecimal.valueOf(amount), defaultCurrency);
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public Money increase(double amount) {
        return new Money(this.amount.add(BigDecimal.valueOf(amount)), currency);
    }

    public Money decrease(double amount) {
        return new Money(this.amount.subtract(BigDecimal.valueOf(amount)), currency);
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public int compareTo(Money other) {
        int currencyComparison = currency.compareTo(other.currency);
        return currencyComparison == 0
                ? amount.compareTo(other.amount)
                : currencyComparison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount)
                && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return this.currency.symbol() + " " + this.amount;
    }
}
