package com.example.test.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.example.test.bean.Product;

public class ProductFilter {

    public static List<Product> filterProducts(List<Product> products, String filterExpression) {
        Predicate<Product> filterPredicate = createFilterPredicate(filterExpression);

        return products.stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }

    private static Predicate<Product> createFilterPredicate(String filterExpression) {
        String[] tokens = filterExpression.split("<|>|=");
        if (tokens.length < 2){
            throw new IllegalArgumentException("Invalid Filter Expression: " + filterExpression);
        }
        String field = tokens[0];
        String value = tokens[1];
        String operator = filterExpression.replace(field, "")
                            .replace(value, "");

        System.out.println(field);
        System.out.println(value);
        System.out.println(operator);

        switch (field) {
            case "count":
                return createNumericPredicate(operator, value, Product::getCount);
            case "name":
                return createStringPredicate(operator, value, Product::getName);
            case "price":
                return createDecimalPredicate(operator, value, Product::getDefaultPrice);
            case "category":
                return createStringPredicate(operator, value, Product::getCategory);
            case "store":
                return createStringPredicate(operator, value, Product::getStore);
            case "date":
                return createDatePredicate(operator, value, Product::getDate);
            default:
                throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    private static Predicate<Product> createNumericPredicate(String operator, String value, ToIntFunction<Product> getter) {
        int intValue = Integer.parseInt(value);
        switch (operator) {
            case ">":
                return product -> getter.applyAsInt(product) > intValue;
            case "<":
                return product -> getter.applyAsInt(product) < intValue;
            case "=":
                return product -> getter.applyAsInt(product) == intValue;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private static Predicate<Product> createDecimalPredicate(String operator, String value, Function<Product, BigDecimal> getter) {
        BigDecimal numValue = new BigDecimal(value);
        switch (operator) {
            case ">":
                return product -> getter.apply(product)
                                    .compareTo(numValue) > 0;
            case "<":
                return product -> getter.apply(product)
                                    .compareTo(numValue) < 0;
            case "=":
                return product -> getter.apply(product)
                                    .compareTo(numValue) == 0;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private static Predicate<Product> createStringPredicate(String operator, String value, Function<Product, String> getter) {
        switch (operator) {
            case "=":
                return product -> getter.apply(product)
                                    .equals(value);

            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private static Predicate<Product> createDatePredicate(String operator, String value, Function<Product, LocalDate> getter) {
        LocalDate dateValue = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        switch (operator) {
            case "=":
                return product -> getter.apply(product)
                                    .isEqual(dateValue);
            case ">":
                return product -> getter.apply(product)
                                    .isAfter(dateValue);
            case "<":
                return product -> getter.apply(product)
                                    .isBefore(dateValue);

            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

}
