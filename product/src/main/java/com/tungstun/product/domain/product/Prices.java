package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Prices {
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_price",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "price_id")
    )
    private List<Price> allPrices;

    public Prices() {
    }
    public Prices(Money initialPrice) {
        this.allPrices = new ArrayList<>(List.of(new Price(initialPrice)));
    }

    public Price currentPrice() {
        return allPrices.stream()
                .filter(Price::isActive)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product doesn't have a current price"));
    }

    public Price atDate(LocalDateTime localDateTime) {
        return allPrices.stream()
                .filter(price -> localDateTime.isAfter(price.getFromDate()))
                .filter(price -> price.getToDate() == null || localDateTime.isBefore(price.getToDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product didn't exist before %s", localDateTime)));
    }

    public void updatePrice(Money newPrice) {
        if (newPrice == null) throw new IllegalArgumentException("Monetary value cannot be null");
        currentPrice().endPricing();
        allPrices.add(new Price(newPrice));
    }
}
