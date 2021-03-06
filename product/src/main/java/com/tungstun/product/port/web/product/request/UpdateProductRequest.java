package com.tungstun.product.port.web.product.request;

public record UpdateProductRequest(
        String name,
        String brand,
        Double size,
        Double price,
        String type,
        Long categoryId,
        Boolean isFavorite) {
}
