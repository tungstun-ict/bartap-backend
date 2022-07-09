package com.tungstun.bill.domain.bill;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;

public interface BillRepository extends CrudRepositoryFragment<Bill, Long> {
    List<Bill> findAllBySessionId(Long id);

    List<Bill> findAllByCustomerId(Long id);

    void delete(Bill bill);
}
