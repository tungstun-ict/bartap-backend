package com.tungstun.bill.port.persistence.bill;

import com.tungstun.bill.domain.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringBillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByIdAndBarId(Long id, Long barId);

    List<Bill> findAllBySessionIdAndBarId(Long sessionId, Long barId);

    List<Bill> findAllByCustomerIdAndBarId(Long customerId, Long barId);
}
