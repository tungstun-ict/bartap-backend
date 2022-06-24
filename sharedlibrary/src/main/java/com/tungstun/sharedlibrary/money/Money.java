package com.tungstun.sharedlibrary.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object to be used for monetary value.
 * */
public class Money implements Comparable<Money>{
    private final BigDecimal amount;
    private final Currency currency;

    public Money(double amount, Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

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

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount.doubleValue();
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
