package com.example.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XHTMLParser {
    public static List<Product> parse(String filePath) {
        List<Product> products = new ArrayList<Product>();

        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            Elements categoryElements = doc.select(".category");
            for (Element categoryElement : categoryElements){
                Elements productElements = categoryElement.select(".product");
                
                for (Element productElement : productElements) {
                    String name = productElement.select(".name").text();
                    String price = productElement.select(".price").text().replace("Цена: ", "");
                    String description = productElement.select(".desc").text().replace("Описание: ", "");
                    String category = categoryElement.id();
                    Product product = new Product(name, price, description, category);
                    products.add(product);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }
}
