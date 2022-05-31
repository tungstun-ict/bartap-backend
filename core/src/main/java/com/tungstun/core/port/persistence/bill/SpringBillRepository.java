package com.tungstun.core.port.persistence.bill;

import com.tungstun.core.domain.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringBillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findAllBySessionId(Long id);
    List<Bill> findAllByCustomerId(Long id);
}
