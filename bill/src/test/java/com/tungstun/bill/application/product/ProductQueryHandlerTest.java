package com.tungstun.bill.application.product;

import com.tungstun.bill.application.product.query.GetProduct;
import com.tungstun.bill.domain.product.Product;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductQueryHandlerTest {
    @Autowired
    private ProductQueryHandler queryHandler;
    @Autowired
    private JpaRepository<Product, Long> repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getPerson() {
        Product product = repository.save(new Product(
                123L,
                456L,
                "name",
                "brand",
                new Money(1)
        ));

        Product retrievedProduct = queryHandler.handle(new GetProduct(product.getId()));

        assertEquals(product.getBarId(), retrievedProduct.getBarId());
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getBrand(), retrievedProduct.getBrand());
        assertEquals(product.getPrice(), retrievedProduct.getPrice());
    }
}
