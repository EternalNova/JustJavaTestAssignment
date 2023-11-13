package com.example.test.FileProcessing;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {
    
    private Path fullPath;

    public JsonWriter(String outputPath, String fileName){
        this.fullPath = Paths.get(outputPath, fileName);
        try{
            Files.createDirectories(Paths.get(outputPath));
        } catch (IOException exception){
            exception.printStackTrace();
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
            exception.printStackTrace();
        }
    }


}