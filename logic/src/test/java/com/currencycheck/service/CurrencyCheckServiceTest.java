package com.currencycheck.service;

import com.currencycheck.model.Currency;
import com.currencycheck.util.CurrencyCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: Document class
 */
public class CurrencyCheckServiceTest {

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
        assertDoesNotThrow(() -> new CurrencyCheckService().getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL), "Service failed for real call. The request failed.");
    }

    @Test
    public void testServiceRealCallResult() {
        Currency currency = null;
        try {
            currency = new CurrencyCheckService().getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL);
        } catch (IOException e) {
            fail("Service failed for real call. The request failed.");
        }

        assertEquals(CurrencyCode.CAD.getCode(), currency.getFromCurrencyCode(), "Service failed for real call. FromCurrencyCode value is incorrect");
        assertEquals(CurrencyCode.BRL.getCode(), currency.getToCurrencyCode(), "Service failed for real call. ToCurrencyCode value is incorrect");

        assertFalse(currency.getFromCurrencyName().isEmpty(), "Service failed for real call. FromCurrencyName value is incorrect");
        assertFalse(currency.getToCurrencyName().isEmpty(), "Service failed for real call. ToCurrencyName value is incorrect");
        assertFalse(currency.getExchangeRate() <= 0, "Service failed for real call. ExchangeRate value is incorrect");
        assertFalse(currency.getLastRefreshed() == null, "Service failed for real call. LastRefreshed value is incorrect");
        assertFalse(currency.getLastRefreshed().getYear() == 0, "Service failed for real call. LastRefreshed value is incorrect");
        assertFalse(currency.getTimeZone().isEmpty(), "Service failed for real call. TimeZone value is incorrect");
    }

}
