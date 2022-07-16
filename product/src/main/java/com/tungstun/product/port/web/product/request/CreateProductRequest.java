package com.tungstun.product.port.web.product.request;

public record CreateProductRequest(
        String name,
        String brand,
        Double size,
        Double price,
        String type,
        Long categoryId) {
}
