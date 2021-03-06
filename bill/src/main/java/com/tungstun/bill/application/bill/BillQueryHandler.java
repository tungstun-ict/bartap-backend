package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.query.*;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import com.tungstun.bill.domain.bill.Order;
import com.tungstun.bill.domain.bill.OrderHistoryEntry;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class BillQueryHandler {
    private final BillRepository repository;

    public BillQueryHandler(BillRepository repository) {
        this.repository = repository;
    }

    public Bill handle(@Valid GetBill query) {
        return repository.findByIdAndBarId(query.id(), query.barId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", query.id())));
    }

    public List<Bill> handle(@Valid ListBillsOfSession query) {
        return repository.findAllOfSession(query.sessionId(), query.barId());
    }

    public List<Bill> handle(@Valid ListBillsOfPerson query) {
        return repository.findAllOfCustomer(query.personId(), query.barId());
    }

    public List<Order> handle(@Valid ListOrdersOfBill query) {
        return handle(new GetBill(query.billId(), query.barId()))
                .getOrders();
    }

    public List<OrderHistoryEntry> handle(@Valid ListOrderHistory query) {
        return handle(new GetBill(query.billId(), query.barId()))
                .getHistory();
    }

    public List<OrderHistoryEntry> handle(@Valid ListSessionOrderHistory query) {
        return handle(new ListBillsOfSession(query.sessionId(), query.barId()))
                .stream()
                .map(Bill::getHistory)
                .flatMap(List::stream)
                .toList();
    }
}
