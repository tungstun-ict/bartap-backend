package com.tungstun.common.money;

import java.io.Serializable;

/**
 * Value object representing the money's currency type
 * */
public record Currency(String symbol, String code) implements Comparable<Currency>, Serializable {
    /**
     * Adds order to currencies based on their currency code. (e.g. 'EUR' for the euro)
     * */
    @Override
    public int compareTo(Currency other) {
        return this.code.compareTo(other.code);
    }
}
