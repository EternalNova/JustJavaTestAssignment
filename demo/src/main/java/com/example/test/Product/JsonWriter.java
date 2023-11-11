package com.example.test.Product;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {
    private String outputPath = "./output";
    private String fileName = "./output0.json";

    private Path fullPath = Paths.get(outputPath, fileName);

    public JsonWriter(String outputPath, String fileName){
        this.outputPath = outputPath;
        this.fileName = fileName;
        this.fullPath = Paths.get(outputPath, fileName);
        try{
            Files.createDirectories(Paths.get(this.outputPath));
        }   
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public JsonWriter(){
    }

    public void writeToJson(List<Product> products) {
        
        try (BufferedWriter writer = Files.newBufferedWriter(this.fullPath, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
            gson.toJson(products, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
