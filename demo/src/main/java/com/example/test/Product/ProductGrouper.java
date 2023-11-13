package com.example.test.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductGrouper {
    public static Map<String, GroupedProduct> groupProductsByField(List<Product> products, String groupingField) {
        return products.stream()
                .collect(Collectors.groupingBy(product -> getFieldValue(product, groupingField),
                        Collectors.collectingAndThen(Collectors.toList(), GroupedProduct::groupedProductFromList)));
    }

    private static String getFieldValue(Product product, String field) {
        switch (field) {
            case "name":
                return product.name;
            case "category":
                return product.category;
            case "store":
                return product.store;

            default:
                throw new IllegalArgumentException("Unsupported grouping field: " + field);
        }
    }

}
