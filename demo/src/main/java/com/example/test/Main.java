package com.example.test;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input_folder = "./input", output_folder = "./output";
        String input_file = "./demo/input/input.xhtml";
        Boolean is_folder_input = false;
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

        if (is_folder_input){
            //...
        }
        else{
            List<Product> products = XHTMLParser.parse(input_file);
            JsonWriter writer = new JsonWriter();
            writer.writeToJson(products);
        }

    }
}