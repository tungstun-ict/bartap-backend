package com.tungstun.bill.port.persistence.product;

import com.tungstun.bill.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductRepository extends JpaRepository<Product, Long> {
}
