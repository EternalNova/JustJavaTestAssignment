package com.example.test.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.example.test.enums.Currency;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @SerializedName("ID")
    @NonNull
    private Integer id;

    @SerializedName("Название товара")
    @NonNull
    private String name;

    @SerializedName("Цена")
    @NonNull
    private Map<Currency, BigDecimal> price;

    @SerializedName("Категория")
    @NonNull
    private String category;

    @SerializedName("Количество")
    @NonNull
    private Integer count;

    @SerializedName("Магазин")
    @NonNull
    private String store;

    @SerializedName("Дата заказа")
    @NonNull
    private LocalDate date;
    
    public BigDecimal getDefaultPrice(){
        return price.get(Currency.USD);
    }

    public BigDecimal getPriceCurrency(Currency currency){
        return price.get(currency);
    }

}