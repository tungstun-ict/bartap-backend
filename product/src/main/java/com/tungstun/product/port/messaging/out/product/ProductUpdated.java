package com.tungstun.product.port.messaging.out.product;

import com.tungstun.product.domain.product.Product;

public record ProductUpdated(
        Long id,
        Long barId,
        String name,
        String brand,
        Double size,
        Double price,
        String currencyCode,
        String currencySymbol) {

    public static ProductUpdated from(Product product) {
        return new ProductUpdated(
                product.getId(),
                product.getBarId(),
                product.getName(),
                product.getBrand(),
                product.getSize(),
                product.getPrice().amount().doubleValue(),
                product.getPrice().currency().code(),
                product.getPrice().currency().symbol()
        );
    }
}
