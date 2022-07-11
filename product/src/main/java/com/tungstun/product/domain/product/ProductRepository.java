package com.tungstun.product.domain.product;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepositoryFragment<Product, Long> {
    Optional<Product> findByIdAndBarId(Long id, Long barId);

    List<Product> findAllOfBar(Long barId);

    List<Product> findAllOfCategory(Long categoryId, Long barId);

    void delete(Long id, Long barId);
}
