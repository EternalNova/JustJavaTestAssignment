package com.example.test.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.example.test.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonWriter {
    
    private Path fullPath;

    public JsonWriter(String outputPath, String fileName){
        this.fullPath = Paths.get(outputPath, fileName);
        try{
            Files.createDirectories(Paths.get(outputPath));
        } catch (IOException exception){
            log.error(exception.getMessage());
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
            log.error(exception.getMessage());
        }
    }


}