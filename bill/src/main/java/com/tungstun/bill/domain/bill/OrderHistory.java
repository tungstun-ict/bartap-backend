package com.tungstun.bill.domain.bill;

import com.tungstun.bill.domain.person.Person;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class OrderHistory {
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderHistoryEntry> history;

    public OrderHistory() {
    }

    public OrderHistory(List<OrderHistoryEntry> history) {
        this.history = history;
    }

    public boolean addEntry(Order order, Person customer) {
        return history.add(new OrderHistoryEntry(
                order.getCreationDate(),
                String.format("%s %s", order.getProduct().getBrand(), order.getProduct().getName()),
                order.getAmount(),
                customer,
                order.getBartender()
        ));
    }

    public List<OrderHistoryEntry> getHistory() {
        return history;
    }
}
