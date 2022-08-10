package com.tungstun.common.money;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing the money's currency type
 */
@Embeddable
public class Currency implements Comparable<Currency> {
    @Column(name = "currency_symbol")
    private String symbol;

    @Column(name = "currency_code")
    private String code;

    public Currency() {
    }

    public Currency(String symbol, String code) {
        this.symbol = symbol;
        this.code = code;
    }

    public String symbol() {
        return symbol;
    }

    public String code() {
        return code;
    }

    /**
     * Adds order to currencies based on their currency code. (e.g. 'EUR' for the euro)
     */
    @Override
    public int compareTo(Currency other) {
        return this.code.compareTo(other.code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
