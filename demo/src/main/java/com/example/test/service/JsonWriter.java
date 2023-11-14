package com.example.test.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.test.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {
    
    private Path fullPath;
    private Logger logger;

    public JsonWriter(String outputPath, String fileName){
        this.fullPath = Paths.get(outputPath, fileName);
        this.logger = LoggerFactory.getLogger(JsonWriter.class);
        try{
            Files.createDirectories(Paths.get(outputPath));
        } catch (IOException exception){
            this.logger.error(exception.getMessage());
        }
    }

    public void writeToJson(Object data) {
        
        try (BufferedWriter writer = Files.newBufferedWriter(this.fullPath, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
            gson.toJson(data, writer);
        } catch (IOException exception) {
            this.logger.error(exception.getMessage());
        }
    }


}