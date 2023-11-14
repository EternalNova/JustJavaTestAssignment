package com.example.test.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GroupedProduct {
    
    @SerializedName("Количество заказов")
    private int totalOrders;
    @SerializedName("Сумма всех заказов")
    private HashMap<Currency, BigDecimal> totalPriceSum;
    @SerializedName("Заказы")
    private List<Product> productList;

    public GroupedProduct(List<Product> productList){
        this.productList = productList;
        this.totalOrders = productList.size();
        this.totalPriceSum = new HashMap<Currency, BigDecimal>(); 
        for (Currency currency : Currency.values()) {
            BigDecimal totalPriceCurrency = productList.stream()
                .filter(product -> product.getPrice().containsKey(currency))
                .map(product -> product.getPriceCurrency(currency).multiply(BigDecimal.valueOf(product.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalPriceSum.put(currency, totalPriceCurrency);
        }
        
    }

    public static GroupedProduct groupedProductFromList(List<Product> productList){
        GroupedProduct groupedProduct = new GroupedProduct(productList);
        return groupedProduct;
    }

}
