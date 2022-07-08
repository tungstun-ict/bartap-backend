package com.tungstun.core.domain.bar;


import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;

public interface BarRepository extends CrudRepositoryFragment<Bar, Long> {
    List<Bar> findAllById(List<Long> barIds);
}
