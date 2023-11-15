package com.example.test;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.test.bean.MainArguments;
import com.example.test.service.FileProcessor;
import com.example.test.utils.MainArgParser;

public class Main {

    public static void main(String[] args) throws IOException {
        
        Logger logger = LoggerFactory.getLogger(Main.class);
        MainArguments config = MainArgParser.parseArguments(args);

        FileProcessor fProcessor = new FileProcessor(config);
        try{
            fProcessor.processInput();
        } catch (Exception exception){
            logger.error(exception.getMessage());
            config.printHelpMessage();
        }

    }

}