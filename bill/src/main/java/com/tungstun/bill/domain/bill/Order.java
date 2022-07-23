package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Embedded
    private OrderProduct product;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    private Person bartender;

    public Order() {
    }

    public Order(Product product, int amount, Person bartender) {
        this.creationDate = ZonedDateTime.now().toLocalDateTime();
        this.product = new OrderProduct(product.getId(), product.getName(), product.getBrand(), product.getPrice());
        this.amount = amount;
        this.bartender = bartender;
    }

    public Double orderPrice() {
        return product.getPrice()
                .amount()
                .multiply(BigDecimal.valueOf(amount))
                .doubleValue();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public OrderProduct getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Person getBartender() {
        return bartender;
    }
}
