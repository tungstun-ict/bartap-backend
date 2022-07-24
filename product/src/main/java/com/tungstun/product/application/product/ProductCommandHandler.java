package com.tungstun.product.application.product;

import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.common.money.Money;
import com.tungstun.product.application.category.CategoryQueryHandler;
import com.tungstun.product.application.category.query.GetCategory;
import com.tungstun.product.application.product.command.CreateProduct;
import com.tungstun.product.application.product.command.DeleteProduct;
import com.tungstun.product.application.product.command.UpdateProduct;
import com.tungstun.product.application.product.event.ProductCreated;
import com.tungstun.product.application.product.event.ProductDeleted;
import com.tungstun.product.application.product.event.ProductUpdated;
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
    private final KafkaMessageProducer producer;

    public ProductCommandHandler(ProductRepository repository, CategoryQueryHandler categoryQueryHandler, KafkaMessageProducer producer) {
        this.repository = repository;
        this.categoryQueryHandler = categoryQueryHandler;
        this.producer = producer;
    }

    private Product loadProduct(Long id, Long barId) {
        return repository.findByIdAndBarId(id, barId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No product found with id %s", id)));
    }

    public Long handle(@Valid CreateProduct command) {
        Category category = categoryQueryHandler.handle(new GetCategory(command.categoryId(), command.barId()));
        Product product = new ProductBuilder(command.barId(), command.name(), category)
                .setBrand(command.brand())
                .setSize(command.size())
                .setPrice(new Money(command.price()))
                .setType(ProductType.getProductType(command.type()))
                .build();
        product = repository.save(product);

        producer.publish(product.getId(), ProductCreated.from(product));

        return product.getId();
    }

    public Long handle(@Valid UpdateProduct command) {
        Product product = loadProduct(command.id(), command.barId());
        if (!product.getCategory().getId().equals(command.categoryId())) {
            Category category = categoryQueryHandler.handle(new GetCategory(command.categoryId(), command.barId()));
            product.setCategory(category);
        }
        product.setName(command.name());
        product.setBrand(command.brand());
        product.setSize(command.size());
        product.setFavorite(command.isFavorite());
        product.setPrice(new Money(command.price()));
        product.setType(ProductType.getProductType(command.type()));

        producer.publish(product.getId(), ProductUpdated.from(product));

        return repository.update(product).getId();
    }

    public void handle(@Valid DeleteProduct command) {
        repository.delete(command.id(), command.barId());

        producer.publish(command.id(), new ProductDeleted(command.id(), command.barId()));
    }
}
