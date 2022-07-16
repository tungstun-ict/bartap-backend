package com.tungstun.product.domain.search;

import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.domain.product.ProductBuilder;
import com.tungstun.product.domain.product.ProductType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductSimilaritySearchAlgorithmTest {
    private static final Long BAR_ID = 123L;
    private static List<Product> allProducts;

    private ProductSimilaritySearchAlgorithm searchAlgorithm;

    @BeforeAll
    static void beforeAll() {
        allProducts = List.of(
                new ProductBuilder(BAR_ID, "product", null).build(),
                new ProductBuilder(BAR_ID, "drink", null).build(),
                new ProductBuilder(BAR_ID, "food", null).build(),
                new ProductBuilder(BAR_ID, "product", null).setBrand("brand").build(),
                new ProductBuilder(BAR_ID, "drink", null).setBrand("tarp company").build(),
                new ProductBuilder(BAR_ID, "food", null).setBrand("bartap").build()
        );
    }

    @BeforeEach
    void setUp() {
        searchAlgorithm = new ProductSimilaritySearchAlgorithm();
    }

    private void print(Collection<Product> products) {
        products.forEach(product -> System.out.println(product.getBrand() + " " + product.getName()));
    }

    @Test
    void searchForExactBrand_ReturnsProductOfBrand() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "brand");

        assertEquals(1, products.size());
    }

    @Test
    void searchForExactBrand_ReturnsProductOfTarpCompany() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "tarp company");

        assertTrue(products.size() >= 1);
    }

    @Test
    void searchForExactBrand_ReturnsProductOfBartap() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "bartap");

        assertEquals(1, products.size());
    }

    @Test
    void searchForPartialBrand_ReturnsProduct() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "tarp co");

        assertEquals(1, products.size());
    }

    @Test
    void searchNameAndBrandCombined_ReturnsProduct() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "product brand");

        assertTrue(products.size() >= 1);
    }

    @Test
    void searchBrandAndNameCombined_ReturnsProduct() {
        Collection<Product> products = searchAlgorithm.apply(allProducts, "product brand");

        assertTrue(products.size() >= 1);
    }
}
