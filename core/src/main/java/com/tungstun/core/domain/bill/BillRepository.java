package com.tungstun.core.domain.bill;

import com.tungstun.core.domain.bar.Bar;
import com.tungstun.sharedlibrary.persistence.CrudRepositoryFragment;

import java.util.List;

public interface BillRepository  extends CrudRepositoryFragment<Bill, Long> {
    List<Bill> findAllBySessionId(Long id);
    List<Bill> findAllByCustomerId(Long id);
}
