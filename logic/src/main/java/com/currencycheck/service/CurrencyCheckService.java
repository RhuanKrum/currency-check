package com.currencycheck.service;

import com.currencycheck.model.Currency;
import com.currencycheck.util.CurrencyCheckJsonParser;
import com.currencycheck.util.CurrencyCode;
import com.currencycheck.util.MessageFormatter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * TODO: Document class
 */
public class CurrencyCheckService implements CurrencyCheckServiceI {

    private static final String RAW_HTTP_LINK;
    private static final HttpClient HTTP_CLIENT;
    private static final String API_KEY;

    static{
        RAW_HTTP_LINK = "https://www.alphavantage.co/query?function={}&from_currency={}&to_currency={}&apikey={}";
        HTTP_CLIENT = HttpClientBuilder.create().build();
        API_KEY = "1ZX2L8AG0WMCZM0L";
    }

    /**
     * TODO: Document method
     * @param function
     * @param fromCurrency
     * @param toCurrency
     * @return
     */
    private static String buildHttpLink(String function, String fromCurrency, String toCurrency){
        return MessageFormatter.format(RAW_HTTP_LINK, function, fromCurrency, toCurrency, API_KEY);
    }

    /**
     * TODO: Document method
     * @param function
     * @param fromCurrency
     * @param toCurrency
     * @return
     * @throws IOException
     */
    private static Currency doRequest(String function, CurrencyCode fromCurrency, CurrencyCode toCurrency) throws IOException{
        HttpGet httpRequest = new HttpGet(buildHttpLink(function, fromCurrency.getValue(), toCurrency.getValue()));
        HttpResponse httpResponse = HTTP_CLIENT.execute(httpRequest);

        JsonObject json = new JsonParser().parse(EntityUtils.toString(httpResponse.getEntity())).getAsJsonObject();

        return CurrencyCheckJsonParser.parseCurrencyExchangeRate(json);
    }

    /**
     * TODO: Document method
     * @param fromCurrency
     * @param toCurrency
     * @return
     * @throws IOException
     */
    public Currency getCurrencyExchangeRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) throws IOException {
        return doRequest("CURRENCY_EXCHANGE_RATE", fromCurrency, toCurrency);
    }
}
