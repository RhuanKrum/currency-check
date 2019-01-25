package com.currencycheck.util;

import com.currencycheck.model.Currency;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TODO: Document class
 */
public final class JsonParser {

    /**
     * Expects the following JSON structure:
     *  {
     *      "Realtime Currency Exchange Rate": {
     *          "1. From_Currency Code": "CAD",
     *          "2. From_Currency Name": "Canadian Dollar",
     *          "3. To_Currency Code": "BRL",
     *          "4. To_Currency Name": "Brazilian Real",
     *          "5. Exchange Rate": "2.82870000",
     *          "6. Last Refreshed": "2019-01-20 15:42:44",
     *          "7. Time Zone": "UTC"
     *      }
     *  }
     * @param json
     * @return
     */
    public static Currency parseCurrencyExchangeRate(JsonObject json){
        String fromCurrencyCode = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("1. From_Currency Code").getAsString();
        String fromCurrencyName = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("2. From_Currency Name").getAsString();
        String toCurrencyCode = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("3. To_Currency Code").getAsString();
        String toCurrencyName = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("4. To_Currency Name").getAsString();
        Double exchangeRate = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("5. Exchange Rate").getAsDouble();
        LocalDateTime lastRefreshed = LocalDateTime.parse(json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("6. Last Refreshed").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String timeZone = json.get("Realtime Currency Exchange Rate").getAsJsonObject().get("7. Time Zone").getAsString();

        Currency currency = new Currency(
                fromCurrencyCode,
                fromCurrencyName,
                toCurrencyCode,
                toCurrencyName,
                exchangeRate,
                lastRefreshed,
                timeZone);

        return currency;
    }
}
