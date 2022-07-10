package com.tungstun.product.domain.product;

import java.util.Locale;

public enum ProductType {
    DRINK,
    FOOD,
    OTHER;

    public static ProductType getProductType(String type) {
        try {
            return ProductType.valueOf(type.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("'%s' is not an existing product type", type), e);
        }
    }
}
