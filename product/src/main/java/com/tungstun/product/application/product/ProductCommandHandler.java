package com.tungstun.product.application.product;

import com.tungstun.common.money.Money;
import com.tungstun.product.application.category.CategoryQueryHandler;
import com.tungstun.product.application.category.query.GetCategory;
import com.tungstun.product.application.product.command.CreateProduct;
import com.tungstun.product.application.product.command.DeleteProduct;
import com.tungstun.product.application.product.command.UpdateProduct;
import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductBuilder;
import com.tungstun.product.domain.product.ProductRepository;
import com.tungstun.product.domain.product.ProductType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class ProductCommandHandler {
    private final ProductRepository repository;
    private final CategoryQueryHandler categoryQueryHandler;

    public ProductCommandHandler(ProductRepository repository, CategoryQueryHandler categoryQueryHandler) {
        this.repository = repository;
        this.categoryQueryHandler = categoryQueryHandler;
    }

    public Long handle(@Valid CreateProduct command) {
        Category category = categoryQueryHandler.handle(new GetCategory(command.categoryId()));
        Product product = new ProductBuilder(command.barId(), command.name(), category)
                .setBrand(command.brand())
                .setSize(command.size())
                .setPrice(new Money(command.price()))
                .setType(ProductType.getProductType(command.type()))
                .build();

        return repository.save(product).getId();
    }

    public Long handle(@Valid UpdateProduct command) {
        Product product = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No product found with id %s", command.id())));
        if (!product.getCategory().getId().equals(command.categoryId())) {
            Category category = categoryQueryHandler.handle(new GetCategory(command.categoryId()));
            product.setCategory(category);
        }
        product.setName(command.name());
        product.setBrand(command.brand());
        product.setSize(command.size());
        product.setFavorite(command.isFavorite());
        product.setPrice(new Money(command.price()));
        product.setType(ProductType.getProductType(command.type()));

        return repository.update(product).getId();
    }

    public void handle(@Valid DeleteProduct command) {
        repository.delete(command.id());
    }
}
