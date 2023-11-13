package com.example.test.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

import com.example.test.Currency.Currency;
import com.google.gson.annotations.SerializedName;

public class Product implements Serializable {

    final private static transient HashMap<String, Currency> currencyMap = new HashMap<String, Currency>(){{
        put("USD", Currency.USD);
        put("RUB", Currency.RUB);
        put("EUR", Currency.EUR);
    }};

    @SerializedName("ID")
    private Integer id;

    @SerializedName("Название товара")
    private String name;

    public String getName(){
        return name;
    }

    @SerializedName("Цена")
    private HashMap<Currency, BigDecimal> price = new HashMap<Currency, BigDecimal>();

    public BigDecimal getDefaultPrice(){
        return price.get(Currency.USD);
    }

    public BigDecimal getPrice(Currency currency){
        return price.get(currency);
    }

    public HashMap<Currency, BigDecimal> getPriceMap(){
        return price;
    }

    @SerializedName("Категория")
    private String category;

    public String getCategory(){
        return category;
    }

    @SerializedName("Количество")
    private Integer count;

    public Integer getCount(){
        return count;
    }

    @SerializedName("Магазин")
    private String store;

    public String getStore(){
        return store;
    }

    @SerializedName("Дата заказа")
    private LocalDate date;

    public LocalDate getDate(){
        return date;
    }

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

}