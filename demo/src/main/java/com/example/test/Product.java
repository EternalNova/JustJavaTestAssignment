package com.example.test;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Product implements Serializable {
    @SerializedName("name")
    public String name;
    @SerializedName("price")
    public String price;
    @SerializedName("description")
    public String description;
    @SerializedName("category")
    public String category;

    public Product(String name, String price, String description, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

}