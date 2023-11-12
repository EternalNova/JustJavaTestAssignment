package com.example.test.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    public static HashMap<Currency, BigDecimal> USD2CurrencyRates =  new HashMap<Currency, BigDecimal>(){{
        put(Currency.USD, new BigDecimal(1).setScale(2));
        put(Currency.RUB, new BigDecimal(90).setScale(2));
        put(Currency.EUR, new BigDecimal(0.93).setScale(2, RoundingMode.HALF_DOWN));
    }};

    public static void convertAll(HashMap<Currency, BigDecimal> prices){

        // Если нет цены в USD, то находим первую валюту и конвертируем ее в USD
        if (!prices.containsKey(Currency.USD)){
            for (Map.Entry<Currency, BigDecimal> entry : prices.entrySet()) {
                Currency entry_Currency = entry.getKey();
                BigDecimal entry_Price = entry.getValue();
                BigDecimal usd_Price = entry_Price.divide(USD2CurrencyRates.get(entry_Currency)); 
                prices.put(entry_Currency, usd_Price);
                break;
            }
        }
        // Конвертируем USD во все остальные
        BigDecimal usd_Price = prices.get(Currency.USD);
        for (Currency currency : Currency.values()){
            if (currency == Currency.USD){
                continue;
            }
            BigDecimal new_Price = usd_Price.multiply(USD2CurrencyRates.get(currency)).setScale(2, RoundingMode.HALF_DOWN);
            prices.put(currency, new_Price);
        }
    }

}
