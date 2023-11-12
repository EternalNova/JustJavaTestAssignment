package com.example.test;

import java.io.IOException;

import com.example.test.FileProcessing.FileProcessor;

public class Main {

    public static void main(String[] args) throws IOException {

        MainConfig config = new MainConfig(args);

        FileProcessor fProcessor = new FileProcessor(config);

        fProcessor.processInput();
    }

}