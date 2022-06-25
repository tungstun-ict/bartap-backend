package com.tungstun.common.money;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Mock currency converter for use in development runtime.<br>
 * Convert() returns the same value in the specified currency.
 * */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile("!prod")
public class MockCurrencyConverter implements CurrencyConverter {
    @Override
    public Money convert(Money money, Currency currency) {
        return new Money(money.amount(), currency);
    }
}
