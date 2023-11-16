package com.example.test.service;

import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;

import com.example.test.bean.Product;
import com.example.test.enums.Currency;

import lombok.val;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
                val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
                products.add(Product.builder()
                    .id(Integer.parseInt(orderElement.attr("id")))
                    .name(orderElement.select(".name").text())
                    .price(Collections.singletonMap(
                        currencyMap.get(orderElement.select(".currency").text()),
                        new BigDecimal(orderElement.select(".price").text())))
                    .category(orderElement.select(".category").text())
                    .count(Integer.parseInt(orderElement.select(".count").text()))
                    .store(orderElement.select(".store_name").text())
                    .date(LocalDate.parse(orderElement.select(".date").text(), dtf))
                    .build());
            }

        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
        }

        return products;
    }

}
