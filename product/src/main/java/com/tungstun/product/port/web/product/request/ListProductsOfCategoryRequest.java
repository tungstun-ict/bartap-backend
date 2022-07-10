package com.tungstun.product.port.web.product.request;

public record ListProductsOfCategoryRequest(
        Long barId,
        Long categoryId) {
}
