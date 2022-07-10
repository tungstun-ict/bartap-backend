package com.tungstun.product.domain.category;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;

public interface CategoryRepository extends CrudRepositoryFragment<Category, Long> {
    List<Category> findAllOfBar(Long barId);
}
