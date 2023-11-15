package com.example.test.utils;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.example.test.bean.MainArguments;

import lombok.Getter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class MainArgParser {

    public static MainArguments parseArguments(String[] args){
        val parser = new BasicParser();
        val formatter = new HelpFormatter();
        val options = new Options();

        options.addOption(new Option("I", "input_file", true, "input file path"));
        options.addOption(new Option("F", "input_folder", true, "input folder path"));
        options.addOption(new Option("O", "output_folder", true, "output folder path"));
        options.addOption(new Option("filter", "filter", true, "Filter Equation (ex. count>5 will filter all products with count lower or equal than 5)"));
        options.addOption(new Option("groupby", "groupby", true, "Group By Field"));

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException exception) {
            log.error("Error", exception);
            formatter.printHelp("order", options);
        }

        val isFolderInput = cmd.hasOption("input_folder");
        val inputFolder = cmd.getOptionValue("input_folder", "./demo/input");
        val outputFolder = cmd.getOptionValue("output_folder", "./output");
        val inputFile = cmd.getOptionValue("input_file", "./demo/input/input.xhtml");
        val filterEquation = cmd.getOptionValue("filter", "");
        val groupByField = cmd.getOptionValue("groupby", "");
        return new MainArguments(isFolderInput, inputFolder, outputFolder, inputFile, filterEquation, groupByField, formatter, options);
    }

}
