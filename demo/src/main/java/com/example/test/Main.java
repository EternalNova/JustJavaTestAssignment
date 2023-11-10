package com.example.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        String input_folder = "./demo/input";
        String output_folder = "./output";
        String input_file = "./demo/input/input.xhtml";
        Boolean is_folder_input = true;
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-F")){
                input_folder = args[i + 1];
                is_folder_input = true;
            }
            else if (args[i].equals("-I")){
                input_file = args[i + 1];
            }
            else if (args[i].equals("-O")){
                output_folder = args[i + 1];
            }
        }

        final String inner_output_folder = output_folder;

        if (is_folder_input){
            try (Stream<Path> paths = Files.walk(Paths.get(input_folder))) {
                paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".xhtml"))
                    .forEach(
                        path -> {
                            System.out.println(path);
                            List<Product> products = XHTMLParser.parse(path.toString());
                            JsonWriter writer = new JsonWriter(inner_output_folder, path.getFileName().toString().replace(".xhtml", ".json"));
                            writer.writeToJson(products);
                        }
                    );
            } 
        }
        else{
            List<Product> products = XHTMLParser.parse(input_file);
            JsonWriter writer = new JsonWriter(output_folder, "output0.json");
            writer.writeToJson(products);
        }

    }
}