package com.tungstun.bill.application.bill;

import com.tungstun.bill.application.bill.command.*;
import com.tungstun.bill.application.bill.event.BillCreated;
import com.tungstun.bill.application.bill.event.BillDeleted;
import com.tungstun.bill.application.bill.event.BillPayed;
import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import com.tungstun.bill.domain.person.Person;
import com.tungstun.bill.domain.person.PersonRepository;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.bill.domain.product.ProductRepository;
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
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    private final KafkaMessageProducer producer;

    public BillCommandHandler(BillRepository repository, ProductRepository productRepository, PersonRepository personRepository, KafkaMessageProducer producer) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.personRepository = personRepository;
        this.producer = producer;
    }

    private Bill loadBill(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", id)));
    }

    private Person loadPerson(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bill found with id %s", personId)));
    }

    public Long handle(@Valid CreateBill command) {
        Person person = loadPerson(command.customerId());
        Bill bill = repository.save(new Bill(command.barId(), command.sessionId(), person));

        producer.publish(bill.getId(), new BillCreated(bill.getId(), bill.getBarId(), bill.getSessionId(), bill.getCustomer().getId()));

        return bill.getId();
    }

    public Long handle(@Valid UpdateBillPayed command) {
        Bill bill = loadBill(command.id(), command.barId());
        bill.setPayed(command.payed());

        producer.publish(command.id(), new BillPayed(bill.getId(), bill.getBarId(), bill.isPayed()));

        return repository.update(bill).getId();
    }

    public void handle(@Valid DeleteBill command) {
        Bill bill = loadBill(command.id(), command.barId());
        repository.delete(bill);

        producer.publish(command.id(), new BillDeleted(bill.getId(), bill.getBarId(), bill.getSessionId(), bill.getCustomer().getId()));
    }

    public void handle(@Valid AddOrder command) {
        Bill bill = loadBill(command.id(), command.barId());
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id %s does not exist", command.productId())));
        Person person = loadPerson(command.bartenderId());
        bill.addOrder(product, command.amount(), person);
        repository.update(bill);
    }

    public void handle(@Valid RemoveOrder command) {
        Bill bill = loadBill(command.id(), command.barId());
        bill.removeOrder(command.orderId());
        repository.update(bill);
    }
}
