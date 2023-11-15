package com.example.test.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @SerializedName("ID")
    private Integer id;

    @SerializedName("Название товара")
    private String name;

    @SerializedName("Цена")
    @Builder.Default
    private Map<Currency, BigDecimal> price = new HashMap<>();

    @SerializedName("Категория")
    private String category;

    @SerializedName("Количество")
    private Integer count;

    @SerializedName("Магазин")
    private String store;

    @SerializedName("Дата заказа")
    private LocalDate date;
    
    public BigDecimal getDefaultPrice(){
        return price.get(Currency.USD);
    }

    public BigDecimal getPriceCurrency(Currency currency){
        return price.get(currency);
    }

}