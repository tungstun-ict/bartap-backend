package com.tungstun.bill.port.messaging.in.product;

import com.tungstun.bill.domain.product.Product;
import com.tungstun.bill.domain.product.ProductRepository;
import com.tungstun.bill.port.messaging.in.product.message.ProductCreated;
import com.tungstun.bill.port.messaging.in.product.message.ProductDeleted;
import com.tungstun.bill.port.messaging.in.product.message.ProductUpdated;
import com.tungstun.common.messaging.KafkaMessageConsumer;
import com.tungstun.common.money.Currency;
import com.tungstun.common.money.Money;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@KafkaListener(id = "productMessageListener", topics = "product")
public class KafkaProductMessageConsumer extends KafkaMessageConsumer {
    private final ProductRepository repository;

    public KafkaProductMessageConsumer(ProductRepository repository) {
        this.repository = repository;
    }

    @KafkaHandler
    public void handleProductCreated(ProductCreated o) {
        LOG.info("Received ProductCreated: {}", o);
        repository.save(new Product(
                o.barId(),
                o.name(),
                o.brand(),
                new Money(
                        BigDecimal.valueOf(o.price()),
                        new Currency(o.currencySymbol(), o.currencyCode())
                )
        ));
    }

    @KafkaHandler
    public void handleProductUpdated(ProductUpdated o) {
        LOG.info("Received ProductUpdated: {}", o);
        Product product = repository.findById(o.id())
                .orElseThrow();

        Money newPrice = new Money(
                BigDecimal.valueOf(o.price()),
                new Currency(o.currencySymbol(), o.currencyCode())
        );
        product.setPrice(newPrice);
        product.setName(o.name());
        product.setBrand(o.brand());
    }

    @KafkaHandler
    public void handleProductDeleted(ProductDeleted o) {
        LOG.info("Received ProductDeleted: {}", o);
        repository.delete(o.id());
    }
}
