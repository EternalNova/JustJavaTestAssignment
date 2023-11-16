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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;


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
                try {
                    val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                .withLocale(Locale.getDefault());
                    val currency = currencyMap.get(orderElement.select(".currency").text());
                    if (currency == null){
                        throw new NoSuchElementException("This currency not supported");
                    }
                    products.add(Product.builder()
                        .id(Integer.parseInt(orderElement.attr("id")))
                        .name(orderElement.select(".name").text())
                        .price(new HashMap<Currency, BigDecimal>(){{
                            put(currency, new BigDecimal(orderElement.select(".price").text()));
                        }})
                        .category(orderElement.select(".category").text())
                        .count(Integer.parseInt(orderElement.select(".count").text()))
                        .store(orderElement.select(".store_name").text())
                        .date(LocalDate.parse(orderElement.select(".date").text(), dtf))
                        .build());
                }
                catch (NumberFormatException exception){
                    logger.error("Can't parse product field: "+exception.getMessage(), exception);
                }
                catch (NoSuchElementException exception){
                    logger.error("Can't parse product field: "+exception.getMessage(), exception);
                }
                catch (DateTimeParseException exception){
                    logger.error("Can't parse product date field: "+exception.getMessage(), exception);
                }
            }

        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
        }

        return products;
    }

}
