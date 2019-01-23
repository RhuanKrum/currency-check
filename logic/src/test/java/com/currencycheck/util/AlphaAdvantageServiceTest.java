package com.currencycheck.util;

import com.currencycheck.model.Currency;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AlphaAdvantageServiceTest {

    @Test
    public void testServiceDemoCall() throws IOException{
        String httpTestUrl = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=JPY&apikey=demo";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpRequest = new HttpGet(httpTestUrl);
        HttpResponse httpResponse = httpClient.execute(httpRequest);

        assertFalse(httpResponse.getEntity().toString().isEmpty(), "Service failed for demo call.");
    }

    @Test
    public void testServiceRealCall() {
        assertDoesNotThrow(() -> AlphaAdvantageService.getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL), "Service failed for real call. The request failed.");
    }

    @Test
    public void testServiceRealCallResult() {
        Currency currency = null;
        try {
            currency = AlphaAdvantageService.getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL);
        } catch (IOException e) {
            fail("Service failed for real call. The request failed.");
        }

        assertEquals(currency.getFromCurrencyCode(), CurrencyCode.CAD, MessageFormatter.format("Service failed for real call. FromCurrencyCode value is incorrect", currency.getFromCurrencyCode()));
        assertEquals(currency.getToCurrencyCode(), CurrencyCode.BRL, MessageFormatter.format("Service failed for real call. ToCurrencyCode value is incorrect", currency.getToCurrencyCode()));
        assertFalse(currency.getFromCurrencyName().isEmpty(), MessageFormatter.format("Service failed for real call. FromCurrencyName value is incorrect", currency.getFromCurrencyName()));
        assertFalse(currency.getToCurrencyName().isEmpty(), MessageFormatter.format("Service failed for real call. ToCurrencyName value is incorrect", currency.getToCurrencyName()));
        assertFalse(currency.getExchangeRate() <= 0, MessageFormatter.format("Service failed for real call. ExchangeRate value is incorrect", currency.getExchangeRate()));
        assertFalse(currency.getLastRefreshed() == null, MessageFormatter.format("Service failed for real call. LastRefreshed value is incorrect", currency.getLastRefreshed()));
        assertFalse(currency.getLastRefreshed().getYear() == 0, MessageFormatter.format("Service failed for real call. LastRefreshed value is incorrect", currency.getLastRefreshed()));
        assertFalse(currency.getTimeZone().isEmpty(), MessageFormatter.format("Service failed for real call. TimeZone value is incorrect", currency.getTimeZone()));
    }

}
