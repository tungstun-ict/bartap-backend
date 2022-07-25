package com.tungstun.bill.port.messaging.in.product;

import com.tungstun.bill.domain.product.ProductRepository;
import com.tungstun.bill.port.messaging.in.product.message.ProductCreated;
import com.tungstun.common.test.MessageConsumerTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
// ApplicationContext will be loaded from the static inner ContextConfiguration class
//@ContextConfiguration(classes= {KafkaConsumerConfig.class, KafkaProducerConfig.class})
class KafkaProductMessageConsumerTest extends MessageConsumerTestBase {
    @Autowired
    private KafkaProductMessageConsumer consumer;
    @Autowired
    private ProductRepository repository;

    @BeforeAll
    static void beforeAll() {
        topic = "product";
    }

    @Test
    void test() {
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
        publishEvent(event.id(), event);

        assertTrue(repository.findById(event.id()).isPresent());
    }
}