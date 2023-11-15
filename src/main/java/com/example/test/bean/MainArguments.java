package com.example.test.bean;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainArguments {

    private Boolean isFolderInput;
    private String inputFolder;
    private String outputFolder;
    private String inputFile;
    private String filterEquation;
    private String groupByField;

    private HelpFormatter formatter;
    private Options options;
    
    public void printHelpMessage(){
        formatter.printHelp("order", options);
    }

}
