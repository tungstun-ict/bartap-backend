package com.tungstun.bill.port.messaging.in.product.message;

public record ProductUpdated(
        Long id,
        String name,
        String brand,
        Double size,
        Double price,
        String currencyCode,
        String currencySymbol) {
}
