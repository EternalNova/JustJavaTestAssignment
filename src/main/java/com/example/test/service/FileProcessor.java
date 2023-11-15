package com.example.test.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.test.bean.GroupedProduct;
import com.example.test.bean.MainArguments;
import com.example.test.bean.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcessor {
    private MainArguments config;
    private final String XHTML = ".xhtml";
    private final String JSON = ".json";

    public FileProcessor(MainArguments config){
        this.config = config;
    }

    public void processInput() {
        if (config.getIsFolderInput()){
            processFolderInput();
        } else {
            processFileInput(
                Paths.get(config.getInputFile())
                    .getFileName()
                    .toString()
                    .replace(XHTML, JSON)
            );
        }

    }

    private void processFileInput(String outputFile){
        List<Product> products = XHTMLParser.parse(this.config.getInputFile());
        products
            .forEach(product -> 
                CurrencyConverter.convertAll(product.getPrice())
            );
        JsonWriter writer = new JsonWriter(this.config.getOutputFolder(), outputFile);
        if (!writer.isFolderCreated){
            return;
        }
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
                        .endsWith(XHTML))
                .forEach(
                    path -> processFileInput(
                        path.getFileName()
                        .toString()
                        .replace(XHTML, JSON)
                    )
                );
        } catch (IOException exception){
            log.error(exception.getMessage());
            this.config.printHelpMessage();
        }
    }

}
