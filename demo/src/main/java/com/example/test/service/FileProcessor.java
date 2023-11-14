package com.example.test.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.test.bean.GroupedProduct;
import com.example.test.bean.Product;
import com.example.test.utils.MainArgParser;

public class FileProcessor {
    private MainArgParser config;
    private Logger logger;
    
    public FileProcessor(MainArgParser config){
        this.config = config;
        this.logger = LoggerFactory.getLogger(FileProcessor.class);
    }

    public void processInput() {
        if (config.getIsFolderInput()){
            processFolderInput();
        } else {
            processFileInput(
                Paths.get(config.getInputFile())
                    .getFileName()
                    .toString()
                    .replace(".xhtml", ".json")
            );
        }

    }

    private void processFileInput(String outputFile){
        List<Product> products = XHTMLParser.parse(this.config.getInputFile());
        products.stream()
            .forEach(product -> 
                CurrencyConverter.convertAll(product.getPrice())
            );
        JsonWriter writer = new JsonWriter(this.config.getOutputFolder(), outputFile);
        if (!config.getFilterEquation().isEmpty()){
            products = ProductFilter.filterProducts(products, config.getFilterEquation());
        }
        if (!config.getGroupByField().isEmpty()){
            Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, config.getGroupByField());
            writer.writeToJson(groupedProducts);
            return;
        }
        writer.writeToJson(products);
    }

    private void processFolderInput(){
        try (Stream<Path> paths = Files.walk(Paths.get(config.getInputFolder()))) {
            paths
                .filter(Files::isRegularFile)
                .filter(
                    path -> path.getFileName()
                        .toString()
                        .endsWith(".xhtml"))
                .forEach(
                    path -> processFileInput(
                        path.getFileName()
                        .toString()
                        .replace(".xhtml", ".json")
                    )
                );
        } catch (IOException exception){
            this.logger.error(exception.getMessage());
            this.config.printHelpMessage();
        }
    }

}
