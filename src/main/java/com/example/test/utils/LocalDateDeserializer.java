package com.example.test.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import lombok.val;

import java.lang.reflect.Type;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    public LocalDate deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context){
        val myDate = json.getAsString();
        val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
        return LocalDate.parse(myDate, dtf);
    }

}