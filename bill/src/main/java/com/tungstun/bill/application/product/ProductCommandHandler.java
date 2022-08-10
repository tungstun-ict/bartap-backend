package com.tungstun.bill.application.product;

import com.tungstun.bill.application.product.command.CreateProduct;
import com.tungstun.bill.application.product.command.DeleteProduct;
import com.tungstun.bill.application.product.command.UpdateProduct;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.bill.domain.product.ProductRepository;
import com.tungstun.common.money.Currency;
import com.tungstun.common.money.Money;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;

@Service
@Validated
@Transactional
public class ProductCommandHandler {
    private final ProductRepository repository;

    public ProductCommandHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Product handle(@Valid CreateProduct command) {
        return repository.save(new Product(
                command.id(),
                command.barId(),
                command.name(),
                command.brand(),
                new Money(
                        BigDecimal.valueOf(command.price()),
                        new Currency(command.currencySymbol(), command.currencyCode())
                )
        ));
    }

    public Product handle(@Valid UpdateProduct command) {
        Product product = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s could not be found", command.id())));

        Money newPrice = new Money(
                BigDecimal.valueOf(command.price()),
                new Currency(command.currencySymbol(), command.currencyCode())
        );
        product.setPrice(newPrice);
        product.setName(command.name());
        product.setBrand(command.brand());
        return repository.update(product);
    }

    public void handle(@Valid DeleteProduct command) {
        repository.delete(command.id());
    }
}
