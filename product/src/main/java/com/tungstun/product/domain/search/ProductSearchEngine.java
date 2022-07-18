package com.tungstun.product.domain.search;

import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class ProductSearchEngine {
    private final ProductSearchAlgorithm searchAlgorithm;
    private final List<Predicate<Product>> filters;
    private String searchText;

    public ProductSearchEngine(ProductSearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
        this.filters = new ArrayList<>();
    }

    public ProductSearchEngine addCategoryIdFilter(Long categoryId) {
        if (categoryId != null) {
            filters.add(product -> product.getCategory().getId().equals(categoryId));
        }
        return this;
    }

    public ProductSearchEngine addIsFavoriteFilter(Boolean isFavorite) {
        if (isFavorite != null) {
            filters.add(product -> product.isFavorite() == isFavorite);
        }
        return this;
    }

    public ProductSearchEngine addProductTypeFilter(ProductType productType) {
        if (productType != null) {
            filters.add(product -> product.getType().equals(productType));
        }
        return this;
    }
    public ProductSearchEngine addCustomFilter(Predicate<Product> predicate) {
        if (predicate != null) {
            filters.add(predicate);
        }
        return this;
    }

    public ProductSearchEngine addSearchText(String searchText) {
        this.searchText = searchText;
        return this;
    }

    public Collection<Product> search(Collection<Product> products) {
        Stream<Product> stream = products.parallelStream();
        for (Predicate<Product> predicate : filters) {
            stream = stream.filter(predicate);
        }
        products = stream.toList();

        if (searchText != null) {
            products = searchAlgorithm.apply(products, searchText);
        }

        return products;
    }
}
