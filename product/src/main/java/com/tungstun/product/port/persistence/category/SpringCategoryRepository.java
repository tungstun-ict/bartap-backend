package com.tungstun.product.port.persistence.category;

import com.tungstun.product.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringCategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByBarId(Long barId);
}
