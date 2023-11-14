package com.example.test.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.test.bean.GroupedProduct;
import com.example.test.bean.Product;

public class ProductGrouper {
    public static Map<String, GroupedProduct> groupProductsByField(List<Product> products, String groupingField) {
        return products.stream()
                .collect(Collectors.groupingBy(product -> getFieldValue(product, groupingField),
                        Collectors.collectingAndThen(Collectors.toList(), GroupedProduct::groupedProductFromList)));
    }

    private static String getFieldValue(Product product, String field) {
        switch (field) {
            case "name":
                return product.getName();
            case "category":
                return product.getCategory();
            case "store":
                return product.getStore();

            default:
                throw new IllegalArgumentException("Unsupported grouping field: " + field);
        }
    }

}
