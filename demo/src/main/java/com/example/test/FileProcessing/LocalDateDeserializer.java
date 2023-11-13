package com.example.test.FileProcessing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    public LocalDate deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context){
        String myDate = json.getAsString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                    .withLocale(Locale.getDefault());
        return LocalDate.parse(myDate, dtf);
    }

}