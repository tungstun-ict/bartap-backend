package com.tungstun.product.port.persistence.category;

import com.tungstun.product.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringCategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndBarId(Long id, Long barId);

    List<Category> findAllByBarId(Long barId);
}
