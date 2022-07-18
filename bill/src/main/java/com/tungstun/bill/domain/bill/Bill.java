package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_payed")
    private boolean isPayed;

    @Column(name = "bar_id")
    private Long barId;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "customer_id")
    private Long customerId;

    @OneToMany
    private List<Order> orders;

    public Bill() {
    }

    public Bill(Long barId, Long sessionId, Long customerId) {
        this.barId = barId;
        this.sessionId = sessionId;
        this.customerId = customerId;
        this.orders = new ArrayList<>();
        this.isPayed = false;
    }

    public double totalPrice() {
        return this.orders.stream()
                .mapToDouble(order -> order.getAmount() * order.getProduct().getPrice().amount().doubleValue())
                .sum();
    }

    public boolean addOrder(Product product, int amount, Person bartender) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (bartender == null) throw new IllegalArgumentException("Bartender cannot be null");
        if (amount < 1) throw new IllegalArgumentException("Amount of products must be above 0");
        Order order = new Order(product, amount, bartender);
        return this.orders.add(order);
    }

    public boolean removeOrder(Long orderId) {
        return orders.removeIf(order -> order.getId().equals(orderId));
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public Long getBarId() {
        return barId;
    }


    public Long getCustomerId() {
        return customerId;
    }
}
