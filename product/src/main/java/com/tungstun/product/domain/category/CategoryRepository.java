package com.tungstun.product.domain.category;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepositoryFragment<Category, Long> {
    Optional<Category> findByIdAndBarId(Long id, Long barId);

    List<Category> findAllOfBar(Long barId);
}
