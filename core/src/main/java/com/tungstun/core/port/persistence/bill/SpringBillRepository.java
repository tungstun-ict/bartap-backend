package com.tungstun.core.port.persistence.bill;

import com.tungstun.core.domain.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBillRepository extends JpaRepository<Bill, Long> {
}
