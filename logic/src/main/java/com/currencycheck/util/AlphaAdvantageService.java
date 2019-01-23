package com.currencycheck.util;

import com.currencycheck.model.Currency;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AlphaAdvantageService {

    private static final String RAW_HTTP_LINK;
    private static final HttpClient HTTP_CLIENT;
    private static final String API_KEY;

    static{
        RAW_HTTP_LINK = "https://www.alphavantage.co/query?function=#&from_currency=#&to_currency=#&apikey=#";
        HTTP_CLIENT = HttpClientBuilder.create().build();
        API_KEY = "1ZX2L8AG0WMCZM0L";
    }

    private static String buildHttpLink(String function, String fromCurrency, String toCurrency){
        return RAW_HTTP_LINK
                .replaceFirst("#", function)
                .replaceFirst("#", fromCurrency)
                .replaceFirst("#", toCurrency)
                .replaceFirst("#", API_KEY);
    }

    private static Currency doRequest(String function, CurrencyCode fromCurrency, CurrencyCode toCurrency) throws IOException{
        HttpGet httpRequest = new HttpGet(buildHttpLink(function, fromCurrency.getCurrencyCode(), toCurrency.getCurrencyCode()));
        HttpResponse httpResponse = HTTP_CLIENT.execute(httpRequest);

        JsonObject json = new JsonParser().parse(EntityUtils.toString(httpResponse.getEntity())).getAsJsonObject();

        return AlphaAdvantageJsonParser.parseCurrencyExchangeRate(json);
    }

    public static Currency getCurrencyExchangeRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) throws IOException {
        return doRequest("CURRENCY_EXCHANGE_RATE", fromCurrency, toCurrency);
    }
}
