package com.tungstun.bill.application.product;

import com.tungstun.bill.application.product.query.GetProduct;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.bill.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
public class ProductQueryHandler {
    private final ProductRepository repository;

    public ProductQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Product handle(@Valid GetProduct query) {
        return repository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s could not be found", query.id())));
    }
}
