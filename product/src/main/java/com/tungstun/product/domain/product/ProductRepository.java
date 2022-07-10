package com.tungstun.product.domain.product;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.List;

public interface ProductRepository extends CrudRepositoryFragment<Product, Long> {
    List<Product> findAllOfBar(Long barId);

    List<Product> findAllOfCategory(Long categoryId);
}
