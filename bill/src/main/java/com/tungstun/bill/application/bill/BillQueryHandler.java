package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.query.GetBill;
import com.tungstun.bill.application.bill.query.ListBillsOfPerson;
import com.tungstun.bill.application.bill.query.ListBillsOfSession;
import com.tungstun.bill.application.bill.query.ListOrdersOfBill;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import com.tungstun.bill.domain.bill.Order;
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
}
