package com.example.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.example.test.Product.JsonWriter;
import com.example.test.Product.Product;
import com.example.test.Product.ProductFilter;
import com.example.test.Product.XHTMLParser;

public class Main {

    public static void main(String[] args) throws IOException {

        MainConfig config = new MainConfig(args);

        final String inner_output_folder = config.output_folder;

        if (config.is_folder_input){
            try (Stream<Path> paths = Files.walk(Paths.get(config.input_folder))) {
                paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".xhtml"))
                    .forEach(
                        path -> proccessFileInput(
                            path.toString(), 
                            inner_output_folder, 
                            path.getFileName().toString().replace(".xhtml", ".json"),
                            config.filter_equation,
                            config.groupbyField
                        )
                    );
            } 
        }
        else{
            proccessFileInput(
                config.input_file, 
                inner_output_folder, 
                Paths.get(config.input_file).getFileName().toString().replace(".xhtml", ".json"),
                config.filter_equation,
                config.groupbyField
            );
        }

    }

    public static void proccessFileInput(String inputFile, String outputFolder, String outputFile, String filter, String groupby){
        List<Product> products = XHTMLParser.parse(inputFile);
        if (!filter.isEmpty()){
            products = ProductFilter.filterProducts(products, filter);
        }
        JsonWriter writer = new JsonWriter(outputFolder, outputFile);
        writer.writeToJson(products);
    }

}