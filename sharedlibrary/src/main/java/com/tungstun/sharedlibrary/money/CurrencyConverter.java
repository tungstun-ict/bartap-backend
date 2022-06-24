package com.tungstun.sharedlibrary.money;

/**
 * Interface used to convert money to another currency.
 * */
public interface CurrencyConverter {
    Money convert(Money money, Currency currency);
}
