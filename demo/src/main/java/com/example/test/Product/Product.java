package com.example.test.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

public class Product implements Serializable {

    public enum Currency{
        USD,
        RUB
    }
    
    private transient HashMap<String, Currency> currencyMap = new HashMap<String, Currency>(){{
        put("USD", Currency.USD);
        put("RUB", Currency.RUB);
    }};

    @SerializedName("Название товара")
    public String name;

    public String getName(){
        return name;
    }

    @SerializedName("Цена")
    public HashMap<Currency, BigDecimal> price = new HashMap<Currency, BigDecimal>();

    public BigDecimal getDefaultPrice(){
        return price.get(Currency.USD);
    }

    @SerializedName("Категория")
    public String category;

    public String getCategory(){
        return category;
    }

    @SerializedName("Количество")
    public Integer count;

    public Integer getCount(){
        return count;
    }

    @SerializedName("Магазин")
    public String store;

    public String getStore(){
        return store;
    }

    @SerializedName("Дата заказа")
    public LocalDate date;

    public LocalDate getDate(){
        return date;
    }

    public Product(String name, String price, String currency, String category, String count, String store, String date) {
        this.name = name;
        this.price.put(currencyMap.get(currency), new BigDecimal(price));
        this.category = category;
        this.count = new Integer(count);
        this.store = store;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.getDefault());
        this.date = LocalDate.parse(date, dtf);
    }

}

class LocalDateAdapter implements JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}