package com.tungstun.product.port.web.product.response;

import com.tungstun.product.domain.product.Product;
import com.tungstun.product.port.web.category.response.CategoryResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ProductResponse(
        Long id,
        Long barId,
        String name,
        String brand,
        double size,
        double price,
        boolean isFavorite,
        CategoryResponse category) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getBarId(),
                product.getName(),
                product.getBrand(),
                product.getSize(),
                product.getPrice().amount().doubleValue(),
                product.isFavorite(),
                CategoryResponse.from(product.getCategory()));
    }
}
