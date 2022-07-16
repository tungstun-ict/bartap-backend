package com.tungstun.bill.domain.bill;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends CrudRepositoryFragment<Bill, Long> {
    Optional<Bill> findByIdAndBarId(Long id, Long barId);

    List<Bill> findAllOfSession(Long sessionId, Long barId);

    List<Bill> findAllOfCustomer(Long customerId, Long barId);

    void delete(Bill bill);
}
