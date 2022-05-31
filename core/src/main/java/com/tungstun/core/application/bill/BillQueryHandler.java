package com.tungstun.core.application.bill;

import com.tungstun.core.application.bill.query.GetBill;
import com.tungstun.core.application.bill.query.ListBillsOfPerson;
import com.tungstun.core.application.bill.query.ListBillsOfSession;
import com.tungstun.core.domain.bill.Bill;
import com.tungstun.core.domain.bill.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Validated
public class BillQueryHandler {
    private final BillRepository repository;

    public BillQueryHandler(BillRepository repository) {
        this.repository = repository;
    }

    public Bill handle(GetBill query) {
        return repository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", query.id())));
    }

    public List<Bill> handle(ListBillsOfSession query) {
        return repository.findAllBySessionId(query.sessionId());
    }

    public List<Bill> handle(ListBillsOfPerson query) {
        return repository.findAllBySessionId(query.personId());
    }
}
