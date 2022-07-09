package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.CreateBill;
import com.tungstun.bill.application.bill.command.DeleteBill;
import com.tungstun.bill.application.bill.command.UpdateBillPayed;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class BillCommandHandler {
    private final BillRepository repository;

    public BillCommandHandler(BillRepository repository) {
        this.repository = repository;
    }

    public Long handle(@Valid CreateBill command) {
        return repository.save(new Bill(command.sessionId(), command.customerId())).getId();
    }

    public Long handle(@Valid UpdateBillPayed command) {
        Bill bill = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", command.id())));
        bill.setPayed(command.payed());
        return repository.update(bill).getId();
    }

    public void handle(@Valid DeleteBill command) {
        repository.delete(command.id());
    }
}
