package com.tungstun.sharedlibrary.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object to be used for monetary value.
 * */
public record Money(BigDecimal amount, Currency currency) implements Comparable<Money>, Serializable {
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public Money increase(double amount) {
        return new Money(this.amount.add(BigDecimal.valueOf(amount)) , currency);
    }

    public Money decrease(double amount) {
        return new Money(this.amount.subtract(BigDecimal.valueOf(amount)) , currency);
    }

    @Override
    public int compareTo(Money other) {
        int currencyComparison = currency.compareTo(other.currency);
        return currencyComparison == 0
                ? amount.compareTo(other.amount)
                : currencyComparison;
    }

    @Override
    public String toString() {
        return this.currency.symbol() + " " + this.amount;
    }
}