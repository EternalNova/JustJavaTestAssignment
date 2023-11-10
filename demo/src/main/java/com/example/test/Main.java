package com.example.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

public class Main {
    public static void main(String[] args) throws IOException {

        Options options = new Options();
        
        Option input_file_Option = new Option("I", "input_file", true, "input file path");
        options.addOption(input_file_Option);
        Option input_folder_Option = new Option("F", "input_folder", true, "input folder path");
        options.addOption(input_folder_Option);
        Option output_folder_Option = new Option("O", "output_folder", true, "output folder path");
        options.addOption(output_folder_Option);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        Boolean is_folder_input = cmd.hasOption("input_folder");
        String input_folder = cmd.getOptionValue("input_folder", "./demo/input");
        String output_folder = cmd.getOptionValue("output_folder", "./output");
        String input_file = cmd.getOptionValue("input_file", "./demo/input/input.xhtml");
        
        final String inner_output_folder = output_folder;

        if (is_folder_input){
            try (Stream<Path> paths = Files.walk(Paths.get(input_folder))) {
                paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".xhtml"))
                    .forEach(
                        path -> {
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