package com.tungstun.product.application.product;

import com.tungstun.common.test.MessageProducerTestBases;
import com.tungstun.product.application.product.command.CreateProduct;
import com.tungstun.product.application.product.command.DeleteProduct;
import com.tungstun.product.application.product.command.UpdateProduct;
import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductCommandHandlerMessageTest extends MessageProducerTestBases {
    @Autowired
    private ProductCommandHandler commandHandler;
    @Autowired
    private JpaRepository<Category, Long> categoryRepository;
    @Autowired
    private JpaRepository<Product, Long> repository;

    private final Long barId = 123L;
    private Category category;

    @BeforeAll
    static void beforeAll() {
        topic = "product";
    }

    @BeforeEach
    protected void setUp() {
        category = categoryRepository.save(new Category("category", barId));
        super.setUp();
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
        repository.deleteAll();
    }

    @Test
    void createProduct_PublishesProductCreated() throws InterruptedException {
        Long id = commandHandler.handle(new CreateProduct(barId, "product", "brand", 330d, 1.3d, "Drink", category.getId()));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("ProductCreated", singleRecord);
    }

    @Test
    void updateProduct_PublishesProductUpdated() throws InterruptedException {
        Long productId = repository.save(new ProductBuilder(barId, "prod", category).build()).getId();

        Long id = commandHandler.handle(new UpdateProduct(
                productId,
                barId,
                "product",
                "brand",
                330d,
                1.3d,
                "Drink",
                category.getId(),
                false));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("ProductUpdated", singleRecord);
    }

    @Test
    void deleteProduct_PublishesProductDeleted() throws InterruptedException {
        Product product = repository.save(new ProductBuilder(barId, "prod", category).build());
        Long id = product.getId();


        commandHandler.handle(new DeleteProduct(id, barId));

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertNotNull(singleRecord);
        assertEventKey(id, singleRecord);
        assertEventType("ProductDeleted", singleRecord);
    }
}

