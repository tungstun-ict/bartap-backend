package com.tungstun.product.domain.product;

import com.tungstun.common.money.Money;
import com.tungstun.product.domain.category.Category;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Where(clause = "deleted = false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bar_id")
    private Long barId;

    @Column(name = "deleted")
    private final boolean deleted = Boolean.FALSE;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "size")
    private double size;

    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Column(name = "price")
    private Money price;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @ManyToOne
    private Category category;

    public Product() {
    }

    public Product(Long barId, String name, String brand, double size, boolean isFavorite, Money price, ProductType type, Category category) {
        this.barId = barId;
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.isFavorite = isFavorite;
        this.price = price;
        this.type = type;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long barId) {
        this.barId = barId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
