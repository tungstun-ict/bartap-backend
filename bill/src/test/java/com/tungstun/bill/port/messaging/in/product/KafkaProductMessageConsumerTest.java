package com.tungstun.bill.port.messaging.in.product;

import com.tungstun.bill.domain.product.Product;
import com.tungstun.bill.port.messaging.in.product.message.ProductCreated;
import com.tungstun.bill.port.messaging.in.product.message.ProductDeleted;
import com.tungstun.bill.port.messaging.in.product.message.ProductUpdated;
import com.tungstun.common.money.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class KafkaProductMessageConsumerTest {
    @Autowired
    private KafkaProductMessageConsumer consumer;
    @Autowired
    private JpaRepository<Product, Long> repository;

    @Test
    void productCreated() {
        ProductCreated event = new ProductCreated(
                123L,
                321L,
                "product",
                "brand",
                330d,
                2.5d,
                "EUR",
                "€"
        );
        consumer.handleProductCreated(event);

        assertTrue(repository.findById(event.id()).isPresent());
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
        ProductUpdated event = new ProductUpdated(
                id,
                "newProduct",
                "newBrand",
                330d,
                2.5d,
                "EUR",
                "€"
        );

        consumer.handleProductUpdated(event);

        Product product = repository.findById(id).orElseThrow();
        assertEquals(event.name(), product.getName());
        assertEquals(event.brand(), product.getBrand());
        assertEquals(event.price(), product.getPrice().amount().doubleValue());
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
        ProductDeleted event = new ProductDeleted(id);

        consumer.handleProductDeleted(event);
        assertTrue(repository.findById(id).isEmpty());
    }
}
