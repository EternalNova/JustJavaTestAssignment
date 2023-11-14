package com.example.test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.test.bean.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XHTMLParser {
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
                String category = orderElement.select(".category").text();
                String count = orderElement.select(".count").text();
                String store = orderElement.select(".store_name").text();
                String date = orderElement.select(".date").text();
                Product product = new Product(id, name, price, currency, category, count, store, date);
                products.add(product);
            }

        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

        return products;
    }
}
