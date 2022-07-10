package com.tungstun.product.application.product.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record UpdateProduct(
        @NotNull(message = "Product id cannot be empty")
        Long id,

        @NotBlank(message = "Product name cannot be blank")
        String name,

        @NotBlank(message = "Product brand cannot be blank")
        String brand,

        @NotNull(message = "Product size cannot be null")
        @PositiveOrZero(message = "Product size cannot be negative")
        Double size,

        @NotNull(message = "Product price cannot be null")
        @PositiveOrZero(message = "Product price cannot be negative")
        Double price,

        @NotBlank(message = "Product type cannot be blank")
        String type,

        @NotBlank(message = "Category id cannot be blank")
        Long categoryId,

        @NotBlank(message = "isFavorite cannot be blank")
        Boolean isFavorite) {
}
