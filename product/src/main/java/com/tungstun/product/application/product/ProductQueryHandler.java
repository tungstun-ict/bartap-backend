package com.tungstun.product.application.product;

import com.tungstun.product.application.product.query.GetProduct;
import com.tungstun.product.application.product.query.ListProductsOfBar;
import com.tungstun.product.application.product.query.ListProductsOfCategory;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductRepository;
import com.tungstun.product.domain.product.ProductType;
import com.tungstun.product.domain.search.ProductSearchAlgorithm;
import com.tungstun.product.domain.search.ProductSearchEngine;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Service
@Validated
public class ProductQueryHandler {
    private final ProductRepository repository;
    private final ProductSearchAlgorithm searchAlgorithm;

    public ProductQueryHandler(ProductRepository repository, ProductSearchAlgorithm searchAlgorithm) {
        this.repository = repository;
        this.searchAlgorithm = searchAlgorithm;
    }

    public Product handle(@Valid GetProduct command) {
        return repository.findByIdAndBarId(command.id(), command.barId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No product found with id %s", command.id())));
    }

    public Collection<Product> handle(@Valid ListProductsOfBar command) {
        List<Product> products = repository.findAllOfBar(command.barId());

        ProductType type = null;
        if (command.productType() != null)  {
            type = ProductType.getProductType(command.productType());
        }

        return new ProductSearchEngine(searchAlgorithm)
                .addCategoryIdFilter(command.categoryId())
                .addIsFavoriteFilter(command.isFavorite())
                .addProductTypeFilter(type)
                .addSearchText(command.searchText())
                .search(products);
    }
}
