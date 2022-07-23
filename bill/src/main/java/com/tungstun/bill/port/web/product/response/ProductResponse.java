package com.tungstun.bill.port.web.product.response;

import com.tungstun.bill.domain.bill.OrderProduct;

public record ProductResponse(
        Long id,
        String name,
        String brand,
        Double price,
        String currencySymbol) {

    public static ProductResponse from(OrderProduct product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice().amount().doubleValue(),
                product.getPrice().currency().symbol()
        );
    }
}
