package com.example.test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.test.bean.Product;
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

    final public static Map<String, Currency> currencyMap = new HashMap(){{
        put("USD", Currency.USD);
        put("RUB", Currency.RUB);
        put("EUR", Currency.EUR);
    }};

    public static List<Product> parse(String filePath) {
        List<Product> products = new ArrayList<Product>();
        
        Logger logger = LoggerFactory.getLogger(XHTMLParser.class);

        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements orderElements = doc.select(".order");

            for (Element orderElement : orderElements) {
                Integer id = Integer.parseInt(orderElement.attr("id"));
                String name = orderElement.select(".name").text();
                String price = orderElement.select(".price").text();
                String currency = orderElement.select(".currency").text();
                Map<Currency, BigDecimal> priceMap = new HashMap<>();
                priceMap.put(currencyMap.get(currency), new BigDecimal(price));
                String category = orderElement.select(".category").text();
                String count = orderElement.select(".count").text();
                String store = orderElement.select(".store_name").text();
                String date = orderElement.select(".date").text();
                Product product = XHTMLParser.parseFromStrings(id, name, price, currency, category, count, store, date);
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
        Integer countInt = new Integer(count);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
        LocalDate dateLocal = LocalDate.parse(date, dtf);
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
