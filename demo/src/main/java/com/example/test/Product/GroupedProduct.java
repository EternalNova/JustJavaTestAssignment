package com.example.test.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.example.test.Currency.Currency;
import com.google.gson.annotations.SerializedName;

public class GroupedProduct {
    
    @SerializedName("Количество заказов")
    private int totalOrders;
    @SerializedName("Сумма всех заказов")
    private HashMap<Currency, BigDecimal> totalPriceSum;
    @SerializedName("Заказы")
    public List<Product> productList;

    public GroupedProduct(List<Product> productList){
        this.productList = productList;
        this.totalOrders = productList.size();
        this.totalPriceSum = new HashMap<Currency, BigDecimal>(); 
        for (Currency currency : Currency.values()) {
            BigDecimal totalPriceCurrency = productList.stream()
                .filter(product -> product.price.containsKey(currency))
                .map(product -> product.price.get(currency).multiply(BigDecimal.valueOf(product.count)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalPriceSum.put(currency, totalPriceCurrency);
        }
        
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public HashMap<Currency, BigDecimal> getTotalPriceSum() {
        return totalPriceSum;
    }

    public static GroupedProduct groupedProductFromList(List<Product> productList){
        GroupedProduct groupedProduct = new GroupedProduct(productList);
        return groupedProduct;
    }

}
