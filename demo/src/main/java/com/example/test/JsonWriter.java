package com.example.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            Files.createDirectories(this.fullPath);
        }   
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public JsonWriter(){
    }

    public void writeToJson(List<Product> products) {
        
        try (BufferedWriter writer = Files.newBufferedWriter(this.fullPath, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(products, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
