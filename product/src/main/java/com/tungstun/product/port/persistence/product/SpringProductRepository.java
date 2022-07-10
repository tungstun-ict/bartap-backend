package com.tungstun.product.port.persistence.product;

import com.tungstun.product.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByBarId(Long barId);

    List<Product> findAllByCategoryId(Long categoryId);
}
