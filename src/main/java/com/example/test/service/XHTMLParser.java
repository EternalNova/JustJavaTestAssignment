package com.example.test.service;

import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;

import com.example.test.bean.Product;

import lombok.val;

import com.example.test.bean.Currency;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class XHTMLParser {

    final private static Map<String, Currency> currencyMap = new HashMap(){{
        put("USD", Currency.USD);
        put("RUB", Currency.RUB);
        put("EUR", Currency.EUR);
    }};

    public static List<Product> parse(String filePath) {
        val products = new ArrayList<Product>();
        
        val logger = LoggerFactory.getLogger(XHTMLParser.class);

        try {
            val input = new File(filePath);
            val doc = Jsoup.parse(input, "UTF-8");
            val orderElements = doc.select(".order");

            for (val orderElement : orderElements) {
                val id = Integer.parseInt(orderElement.attr("id"));
                val name = orderElement.select(".name").text();
                val price = orderElement.select(".price").text();
                val currency = orderElement.select(".currency").text();
                val priceMap = new HashMap<>();
                priceMap.put(currencyMap.get(currency), new BigDecimal(price));
                val category = orderElement.select(".category").text();
                val count = orderElement.select(".count").text();
                val store = orderElement.select(".store_name").text();
                val date = orderElement.select(".date").text();
                val product = XHTMLParser.parseFromStrings(id, name, price, currency, category, count, store, date);
                products.add(product);
            }

        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

        return products;
    }

    public static Product parseFromStrings(Integer id, String name, String price, String currency, String category, String count, String store, String date){
        Map<Currency, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(currencyMap.get(currency), new BigDecimal(price));
        val countInt = new Integer(count);
        val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
        val dateLocal = LocalDate.parse(date, dtf);
        return Product.builder()
                            .id(id)
                            .name(name)
                            .price(priceMap)
                            .category(category)
                            .count(countInt)
                            .store(store)
                            .date(dateLocal)
                            .build();

    }

}
