package com.tungstun.product.application.product;

import com.tungstun.product.application.product.query.GetProduct;
import com.tungstun.product.application.product.query.ListProductsOfBar;
import com.tungstun.product.application.product.query.ListProductsOfCategory;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ProductQueryHandler {
    private final ProductRepository repository;

    public ProductQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Product handle(@Valid GetProduct command) {
        return repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No product found with id %s", command.id())));
    }

    public List<Product> handle(@Valid ListProductsOfBar command) {
        return repository.findAllOfBar(command.barId());
    }

    public List<Product> handle(@Valid ListProductsOfCategory command) {
        return repository.findAllOfCategory(command.categoryId(), command.barId());
    }
}
