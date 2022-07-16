package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.CreateBill;
import com.tungstun.bill.application.bill.command.DeleteBill;
import com.tungstun.bill.application.bill.command.UpdateBillPayed;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import com.tungstun.bill.port.messaging.out.BillCreated;
import com.tungstun.bill.port.messaging.out.BillDeleted;
import com.tungstun.common.messaging.KafkaMessageProducer;
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
    private final KafkaMessageProducer producer;

    public BillCommandHandler(BillRepository repository, KafkaMessageProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    private Bill loadBill(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", id)));
    }

    public Long handle(@Valid CreateBill command) {
        Bill bill = repository.save(new Bill(command.barId(), command.sessionId(), command.customerId()));

        producer.publish(bill.getId(), new BillCreated(bill.getId(), bill.getBarId(), bill.getSessionId(), bill.getCustomerId()));

        return bill.getId();
    }

    public Long handle(@Valid UpdateBillPayed command) {
        Bill bill = loadBill(command.id(), command.barId());
        bill.setPayed(command.payed());
        return repository.update(bill).getId();
    }

    public void handle(@Valid DeleteBill command) {
        Bill bill = loadBill(command.id(), command.barId());
        repository.delete(bill);

        producer.publish(command.id(), new BillDeleted(bill.getId(), bill.getBarId(), bill.getSessionId(), bill.getCustomerId()));
    }
}
