package com.example.test;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainConfig {
    public Boolean is_folder_input = true;
    public String input_folder;
    public String output_folder;
    public String input_file;
    public String filter_equation;
    public String groupbyField;

    private CommandLineParser parser = new BasicParser();
    private HelpFormatter formatter = new HelpFormatter();
    private Options options = new Options();

    public MainConfig(String[] args){
                
        Option input_file_Option = new Option("I", "input_file", true, "input file path");
        options.addOption(input_file_Option);
        Option input_folder_Option = new Option("F", "input_folder", true, "input folder path");
        options.addOption(input_folder_Option);
        Option output_folder_Option = new Option("O", "output_folder", true, "output folder path");
        options.addOption(output_folder_Option);
        Option filter_Option = new Option("filter", "filter", true, "Filter Equation (ex. count>5 will filter all products with count lower or equal than 5)");
        options.addOption(filter_Option);
        Option groupby_Option = new Option("groupby", "groupby", true, "Group By Field");
        options.addOption(groupby_Option);

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("order", options);

            System.exit(1);
        }

        this.is_folder_input = cmd.hasOption("input_folder");
        this.input_folder = cmd.getOptionValue("input_folder", "./demo/input");
        this.output_folder = cmd.getOptionValue("output_folder", "./output");
        this.input_file = cmd.getOptionValue("input_file", "./demo/input/input.xhtml");
        this.filter_equation = cmd.getOptionValue("filter", "");
        this.groupbyField = cmd.getOptionValue("groupby", "");
    }

    public void printHelpMessage(){
        formatter.printHelp("order", options);
    }

}
