package com.example.test;

import com.example.test.service.FileProcessor;
import com.example.test.utils.MainArgParser;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        val config = MainArgParser.parseArguments(args);

        val fProcessor = new FileProcessor(config);
        try{
            fProcessor.processInput();
        } catch (Exception exception){
            log.error(exception.getMessage(), exception);
            config.printHelpMessage();
        }

    }

}