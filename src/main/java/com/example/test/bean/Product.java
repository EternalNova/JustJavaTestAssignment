package com.example.test.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Product implements Serializable {

    final private static Map<String, Currency> currencyMap = new HashMap(){{
        put("USD", Currency.USD);
        put("RUB", Currency.RUB);
        put("EUR", Currency.EUR);
    }};

    @SerializedName("ID")
    private Integer id;

    @SerializedName("Название товара")
    private String name;

    @SerializedName("Цена")
    private Map<Currency, BigDecimal> price = new HashMap<>();

    @SerializedName("Категория")
    private String category;

    @SerializedName("Количество")
    private Integer count;

    @SerializedName("Магазин")
    private String store;

    @SerializedName("Дата заказа")
    private LocalDate date;

    public Product(Integer id, String name, String price, String currency, String category, String count, String store, String date) {
        this.id = id;
        this.name = name;
        this.price.put(currencyMap.get(currency), new BigDecimal(price));
        this.category = category;
        this.count = new Integer(count);
        this.store = store;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
        this.date = LocalDate.parse(date, dtf);
    }
    
    public BigDecimal getDefaultPrice(){
        return price.get(Currency.USD);
    }

    public BigDecimal getPriceCurrency(Currency currency){
        return price.get(currency);
    }

}