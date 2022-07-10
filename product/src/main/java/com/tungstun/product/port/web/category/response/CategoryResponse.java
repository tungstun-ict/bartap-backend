package com.tungstun.product.port.web.category.response;

import com.tungstun.product.domain.category.Category;

public record CategoryResponse(
        Long id,
        String name,
        Long barId) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getBarId());
    }
}
