package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;
import com.tungstun.product.domain.category.Category;

public class ProductBuilder {
    private final String name;
    private final String brand;
    private final Category category;
    private double size = 0;
    private boolean isFavorite = false;
    private Money price = new Money(0.0);
    private ProductType type = ProductType.OTHER;

    public ProductBuilder(String name, String brand, Category category) {
        this.name = name;
        this.brand = brand;
        this.category = category;
    }

    public ProductBuilder setSize(double size) {
        this.size = size;
        return this;
    }

    public ProductBuilder setFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }

    public ProductBuilder setPrice(Money price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setType(ProductType type) {
        this.type = type;
        return this;
    }

    public Product build() {
        return new Product(
                this.name,
                this.brand,
                this.size,
                this.isFavorite,
                this.price,
                this.type,
                this.category);
    }
}
