package com.tungstun.product.domain.search;

import com.tungstun.product.domain.product.Product;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductSimilaritySearchAlgorithm implements ProductSearchAlgorithm {
    private static final float THRESHOLD = 0.68f;
    private static final float REGEX_MULTIPLIER = 1.4f;
    private static final JaccardSimilarity JACCARD_SIMILARITY = new JaccardSimilarity();

    @Override
    public Collection<Product> apply(Collection<Product> products, String searchText) {
        Map<Product, Double> indexedProducts = products.parallelStream()
                .collect(Collectors.toMap(
                        product -> product,
                        product -> calculateSearchIndex(product, searchText)
                ));

        return indexedProducts.entrySet().parallelStream()
                .filter(entry -> entry.getValue() >= THRESHOLD)
                .peek( entry -> System.out.println(entry.getKey().getName() + " " + entry.getKey().getBrand() + " " + entry.getValue()))
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .map(Map.Entry::getKey)
                .toList();

    }

    private Double calculateSearchIndex(Product product, String searchString) {
        String brand = product.getBrand();
        String name = product.getName();
        String completeName = brand + " " + name;
        String completeNameReversed = name + " " + brand;
        Pattern regexPattern =  Pattern.compile("(.*)" + searchString + "(.*)", Pattern.CASE_INSENSITIVE);

        return Stream.of(completeName, completeNameReversed, brand, name)
                .map(right -> JACCARD_SIMILARITY.apply(searchString, right)) // Calculate similarity index of all string combinations
                .reduce((l, r) -> l > r ? l : r) // Reduce stream to the highest similarity index value
                .map(similarity -> { // Apply relative bonus and multiplier if regex pattern matches
                    if (regexPattern.matcher(completeName).find() || regexPattern.matcher(completeNameReversed).find()) {
                        similarity = (double) searchString.length() / completeName.length() + 0.3f * REGEX_MULTIPLIER;
                    }
                    return similarity;
                })
                .orElse(0d); // Default case: lowest similarity
    }
}
