package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Embedded
    private Bartender bartender;

    public Order() {
    }

    public Order(Product product, int amount, Bartender bartender) {
        this.creationDate = ZonedDateTime.now().toLocalDateTime();
        this.product = product;
        this.amount = amount;
        this.bartender = bartender;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Bartender getBartender() {
        return bartender;
    }

    public void setBartender(Bartender bartender) {
        this.bartender = bartender;
    }
}
