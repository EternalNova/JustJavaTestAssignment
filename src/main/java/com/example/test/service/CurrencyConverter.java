package com.example.test.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.example.test.enums.Currency;

import lombok.val;

public class CurrencyConverter {
    final private static Map<Currency, BigDecimal> USD2CurrencyRates =  new HashMap<Currency, BigDecimal>(){{
        put(Currency.USD, new BigDecimal(1).setScale(2));
        put(Currency.RUB, new BigDecimal(90).setScale(2));
        put(Currency.EUR, new BigDecimal(0.93).setScale(2, RoundingMode.HALF_DOWN));
    }};

    public static void convertAll(Map<Currency, BigDecimal> prices){
        val logger = LoggerFactory.getLogger(XHTMLParser.class);
        // Если нет цены в USD, то находим первую валюту и конвертируем ее в USD
        if (!prices.containsKey(Currency.USD)){
            for (val entry : prices.entrySet()) {
                val entryCurrency = entry.getKey();
                val entryPrice = entry.getValue();
                val usdPrice = entryPrice.divide(USD2CurrencyRates.get(entryCurrency)).setScale(2, RoundingMode.HALF_DOWN); 
                prices.put(Currency.USD, usdPrice);
                break;
            }
        }
        // Конвертируем USD во все остальные
        val usdPrice = prices.get(Currency.USD);
        for (val currency : Currency.values()){
            if (currency == Currency.USD){
                continue;
            }
            
            val newPrice = usdPrice.multiply(
                USD2CurrencyRates.get(currency))
                .setScale(2, RoundingMode.HALF_DOWN
            );
            prices.put(currency, newPrice);
        }
    }

}
