package com.tungstun.product.port.persistence;

import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.category.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultCategoryRepository implements CategoryRepository {
    private final SpringCategoryRepository repository;

    public DefaultCategoryRepository(SpringCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category save(Category entity) {
        return repository.save(entity);
    }

    @Override
    public Category update(Category entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findAllOfBar(Long barId) {
        return repository.findAllByBarId(barId);
    }
}
