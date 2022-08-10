package com.tungstun.bill.application.product;

import com.tungstun.bill.application.product.command.CreateProduct;
import com.tungstun.bill.application.product.command.DeleteProduct;
import com.tungstun.bill.application.product.command.UpdateProduct;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProductCommandHandlerTest {
    @Autowired
    private ProductCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Product, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void productCreated() {
        CreateProduct command = new CreateProduct(
                123L,
                321L,
                "product",
                "brand",
                330d,
                "EUR",
                "€"
        );

        commandHandler.handle(command);

        assertTrue(repository.findById(command.id()).isPresent());
    }

    @Test
    void productUpdated() {
        Long id = repository.save(new Product(
                123L,
                456L,
                "name",
                "brand",
                new Money(1)
        )).getId();
        UpdateProduct command = new UpdateProduct(
                id,
                "newProduct",
                "newBrand",
                330d,
                "EUR",
                "€"
        );

        commandHandler.handle(command);

        Product product = repository.findById(id).orElseThrow();
        assertEquals(command.name(), product.getName());
        assertEquals(command.brand(), product.getBrand());
        assertEquals(command.price(), product.getPrice().amount().doubleValue());
    }

    @Test
    void productDeleted() {
        Long id = repository.save(new Product(
                123L,
                456L,
                "name",
                "brand",
                new Money(1)
        )).getId();
        DeleteProduct command = new DeleteProduct(id);

        commandHandler.handle(command);

        assertTrue(repository.findById(id).isEmpty());
    }
}
