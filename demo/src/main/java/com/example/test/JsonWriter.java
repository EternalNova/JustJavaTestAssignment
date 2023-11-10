package com.example.test;

import java.io.FileWriter;
import java.io.IOException;
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
        try (FileWriter writer = new FileWriter(this.fullPath.toString())) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(products, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
