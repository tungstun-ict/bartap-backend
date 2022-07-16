package com.tungstun.product.port.persistence.product;

import com.tungstun.product.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndBarId(Long id, Long barId);

    List<Product> findAllByBarId(Long barId);

    List<Product> findAllByCategoryIdAndBarId(Long categoryId, Long barId);

    void deleteByIdAndBarId(Long id, Long barId);
}
