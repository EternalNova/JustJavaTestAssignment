package com.example.test.Product;

import java.math.BigDecimal;
import java.util.List;

import com.example.test.Product.Product.Currency;
import com.google.gson.annotations.SerializedName;

public class GroupedProduct {
    
    @SerializedName("Количество заказов")
    private int totalOrders;
    @SerializedName("Сумма всех заказов (в USD)")
    private BigDecimal totalPriceSum;
    @SerializedName("Заказы")
    public List<Product> productList;

    // public GroupedProduct() {
    //     this.totalOrders = 0;
    //     this.totalPriceSum = BigDecimal.ZERO;
    // }

    public GroupedProduct(List<Product> productList){
        this.productList = productList;
        this.totalOrders = productList.size();
        this.totalPriceSum = productList.stream()
            .map(product -> product.price.get(Currency.USD).multiply(BigDecimal.valueOf(product.count)))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public BigDecimal getTotalPriceSum() {
        return totalPriceSum;
    }

    public static GroupedProduct groupedProductFromList(List<Product> productList){
        GroupedProduct groupedProduct = new GroupedProduct(productList);
        return groupedProduct;
    }

}
