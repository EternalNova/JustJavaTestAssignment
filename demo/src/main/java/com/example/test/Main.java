package com.example.test;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        MainConfig config = new MainConfig(args);

        FileProcessor fProcessor = new FileProcessor(config);

        fProcessor.processInput();
    }

}