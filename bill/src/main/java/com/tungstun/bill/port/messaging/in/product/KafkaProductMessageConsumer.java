package com.tungstun.bill.port.messaging.in.product;

import com.tungstun.bill.application.product.ProductCommandHandler;
import com.tungstun.bill.application.product.command.CreateProduct;
import com.tungstun.bill.application.product.command.DeleteProduct;
import com.tungstun.bill.application.product.command.UpdateProduct;
import com.tungstun.bill.port.messaging.in.product.message.ProductCreated;
import com.tungstun.bill.port.messaging.in.product.message.ProductDeleted;
import com.tungstun.bill.port.messaging.in.product.message.ProductUpdated;
import com.tungstun.common.messaging.KafkaMessageConsumer;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "productMessageListener", topics = "product")
public class KafkaProductMessageConsumer extends KafkaMessageConsumer {
    private final ProductCommandHandler commandHandler;

    public KafkaProductMessageConsumer(ProductCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handleProductCreated(ProductCreated event) {
        LOG.info("Received ProductCreated: {}", event);
        commandHandler.handle(new CreateProduct(
                event.id(),
                event.barId(),
                event.name(),
                event.brand(),
                event.price(),
                event.currencySymbol(),
                event.currencyCode()
        ));
    }

    @KafkaHandler
    public void handleProductUpdated(ProductUpdated event) {
        LOG.info("Received ProductUpdated: {}", event);

        commandHandler.handle(new UpdateProduct(
                event.id(),
                event.name(),
                event.brand(),
                event.price(),
                event.currencySymbol(),
                event.currencyCode()
        ));
    }

    @KafkaHandler
    public void handleProductDeleted(ProductDeleted event) {
        LOG.info("Received ProductDeleted: {}", event);
        commandHandler.handle(new DeleteProduct(event.id()));
    }
}
