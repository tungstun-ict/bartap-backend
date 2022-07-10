package com.tungstun.product.port.persistence.product;

import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultProductRepository implements ProductRepository {
    private final SpringProductRepository repository;

    public DefaultProductRepository(SpringProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product entity) {
        return repository.save(entity);
    }

    @Override
    public Product update(Product entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAllOfBar(Long barId) {
        return repository.findAllByBarId(barId);
    }

    @Override
    public List<Product> findAllOfCategory(Long categoryId) {
        return repository.findAllByCategoryId(categoryId);
    }
}
