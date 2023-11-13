package com.example.test;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainConfig {
    private Boolean isFolderInput;
    public Boolean getIsFolderInput() {
        return isFolderInput;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getFilterEquation() {
        return filterEquation;
    }

    public String getGroupByField() {
        return groupByField;
    }

    private String inputFolder;
    private String outputFolder;
    private String inputFile;
    private String filterEquation;
    private String groupByField;

    private CommandLineParser parser = new BasicParser();
    private HelpFormatter formatter = new HelpFormatter();
    private Options options = new Options();

    public MainConfig(String[] args){
                
        Option inputFileOption = new Option("I", "input_file", true, "input file path");
        options.addOption(inputFileOption);
        Option inputFolderOption = new Option("F", "input_folder", true, "input folder path");
        options.addOption(inputFolderOption);
        Option outputFolderOption = new Option("O", "output_folder", true, "output folder path");
        options.addOption(outputFolderOption);
        Option filterOption = new Option("filter", "filter", true, "Filter Equation (ex. count>5 will filter all products with count lower or equal than 5)");
        options.addOption(filterOption);
        Option groupbyOption = new Option("groupby", "groupby", true, "Group By Field");
        options.addOption(groupbyOption);

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException exception) {
            System.out.println(exception.getMessage());
            formatter.printHelp("order", options);
        }

        this.isFolderInput = cmd.hasOption("input_folder");
        this.inputFolder = cmd.getOptionValue("input_folder", "./demo/input");
        this.outputFolder = cmd.getOptionValue("output_folder", "./output");
        this.inputFile = cmd.getOptionValue("input_file", "./demo/input/input.xhtml");
        this.filterEquation = cmd.getOptionValue("filter", "");
        this.groupByField = cmd.getOptionValue("groupby", "");
    }

    public void printHelpMessage(){
        formatter.printHelp("order", options);
    }

}
