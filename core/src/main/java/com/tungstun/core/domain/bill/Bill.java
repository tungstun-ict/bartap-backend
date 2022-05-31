package com.tungstun.core.domain.bill;

import javax.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_payed")
    private boolean isPayed;

    @Column(name = "session_id")
    private Long sessionId;

//    @Column(name = "customer_id")
//    private Long customer_id

//    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Order> orders;

    public Bill() { }

    public Bill(boolean isPayed, Long sessionId) {
        this.isPayed = isPayed;
        this.sessionId = sessionId;
    }

    public Long getId() { return id; }

    public Long getSessionId() {
        return sessionId;
    }

//    public Long getCustomerId() { return customerId; }

//    public List<Order> getOrders() { return this.orders; }

//    public boolean addOrder(Product product, int amount, Person bartender){
//        if (product == null || amount < 1 || bartender == null) return false;
//        Order order = new Order(product, amount, this, bartender);
//        return this.orders.add(order);
//    }
//
//    public boolean removeOrder(Order order){
//        return this.orders.remove(order);
//    }

    public boolean isPayed() { return isPayed; }

    public void setPayed(boolean payed) { isPayed = payed; }

//    public double calculateTotalPrice() {
//        return this.orders.stream()
//                .mapToDouble(order -> order.getProduct().getPrice() * order.getAmount())
//                .sum();
//    }
}
