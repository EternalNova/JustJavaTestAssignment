package com.example.test.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.example.test.bean.Currency;

public class CurrencyConverter {
    final private static Map<Currency, BigDecimal> USD2CurrencyRates =  new HashMap<Currency, BigDecimal>(){{
        put(Currency.USD, new BigDecimal(1).setScale(2));
        put(Currency.RUB, new BigDecimal(90).setScale(2));
        put(Currency.EUR, new BigDecimal(0.93).setScale(2, RoundingMode.HALF_DOWN));
    }};

    public static void convertAll(Map<Currency, BigDecimal> prices){
        // Если нет цены в USD, то находим первую валюту и конвертируем ее в USD
        if (!prices.containsKey(Currency.USD)){
            for (Map.Entry<Currency, BigDecimal> entry : prices.entrySet()) {
                Currency entryCurrency = entry.getKey();
                BigDecimal entryPrice = entry.getValue();
                BigDecimal usdPrice = entryPrice.divide(USD2CurrencyRates.get(entryCurrency)); 
                prices.put(entryCurrency, usdPrice);
                break;
            }
        }
        // Конвертируем USD во все остальные
        BigDecimal usdPrice = prices.get(Currency.USD);
        for (Currency currency : Currency.values()){
            if (currency == Currency.USD){
                continue;
            }
            BigDecimal newPrice = usdPrice.multiply(
                USD2CurrencyRates.get(currency))
                .setScale(2, RoundingMode.HALF_DOWN
            );
            prices.put(currency, newPrice);
        }
    }

}
