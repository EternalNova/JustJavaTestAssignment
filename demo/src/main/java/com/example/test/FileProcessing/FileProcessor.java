package com.example.test.FileProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.test.MainConfig;
import com.example.test.Currency.CurrencyConverter;
import com.example.test.Product.GroupedProduct;
import com.example.test.Product.Product;
import com.example.test.Product.ProductFilter;
import com.example.test.Product.ProductGrouper;

public class FileProcessor {
    MainConfig config;

    public FileProcessor(MainConfig config){
        this.config = config;
    }

    public void processInput() throws IOException {
        if (config.is_folder_input){
            processFolderInput();
        }
        else{
            processFileInput(
                Paths.get(config.input_file).getFileName().toString().replace(".xhtml", ".json")
            );
        }

    }

    public void processFileInput(String outputFile){
        List<Product> products = XHTMLParser.parse(this.config.input_file);
        products.stream().forEach(product -> {CurrencyConverter.convertAll(product.price);});
        JsonWriter writer = new JsonWriter(this.config.output_folder, outputFile);
        if (!config.filter_equation.isEmpty()){
            products = ProductFilter.filterProducts(products, config.filter_equation);
        }
        if (!config.groupbyField.isEmpty()){
            Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, config.groupbyField);
            writer.writeToJson(groupedProducts);
            return;
        }
        writer.writeToJson(products);
    }

    public void processFolderInput() throws IOException{
        try (Stream<Path> paths = Files.walk(Paths.get(config.input_folder))) {
            paths
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(".xhtml"))
                .forEach(
                    path -> {processFileInput(
                        path.getFileName().toString().replace(".xhtml", ".json")
                    );}
                );
        } 
    }

}
