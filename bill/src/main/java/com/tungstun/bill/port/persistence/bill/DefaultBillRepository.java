package com.tungstun.bill.port.persistence.bill;

import com.tungstun.bill.domain.bill.Bill;
import com.tungstun.bill.domain.bill.BillRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultBillRepository implements BillRepository {
    private final SpringBillRepository repository;

    public DefaultBillRepository(SpringBillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bill save(Bill bill) {
        return repository.save(bill);
    }

    @Override
    public Bill update(Bill bill) {
        return repository.save(bill);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bill> findAllBySessionId(Long id) {
        return repository.findAllBySessionId(id);
    }

    @Override
    public List<Bill> findAllByCustomerId(Long id) {
        return repository.findAllByCustomerId(id);
    }
}
