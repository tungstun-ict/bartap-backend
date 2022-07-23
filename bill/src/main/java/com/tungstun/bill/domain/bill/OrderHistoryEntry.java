package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history_entry")
public class OrderHistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime date;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    private Person customer;

    @ManyToOne
    private Person bartender;

    public OrderHistoryEntry() {
    }

    public OrderHistoryEntry(LocalDateTime date, String productName, Integer amount, Person customer, Person bartender) {
        this.date = date;
        this.productName = productName;
        this.amount = amount;
        this.customer = customer;
        this.bartender = bartender;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getAmount() {
        return amount;
    }

    public Person getCustomer() {
        return customer;
    }

    public Person getBartender() {
        return bartender;
    }
}
