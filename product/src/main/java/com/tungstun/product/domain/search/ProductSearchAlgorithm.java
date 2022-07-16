package com.tungstun.product.domain.search;

import com.tungstun.product.domain.product.Product;

import java.util.Collection;

public interface ProductSearchAlgorithm {
    Collection<Product> apply(Collection<Product> products, String searchText);
}
