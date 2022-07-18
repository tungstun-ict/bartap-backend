package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "money")
    private Money money;

    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @Column(name = "to_date")
    private LocalDateTime toDate;

    public Price() {
    }

    public Price(Money money) {
        this.money = money;
        this.fromDate = LocalDateTime.now();
    }

    public boolean isActive() {
        return toDate == null;
    }

    public void endPricing() {
        this.toDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Money getMoney() {
        return money;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }
}
