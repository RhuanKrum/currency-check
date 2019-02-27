package com.currencycheck.model;

import com.currencycheck.util.CurrencyFormatter;

import java.time.LocalDateTime;

/**
 * TODO: Document class
 */
public class Currency {

    private String fromCurrencyCode;
    private String fromCurrencyName;
    private String toCurrencyCode;
    private String toCurrencyName;
    private Double exchangeRate;
    private LocalDateTime lastRefreshed;
    private String timeZone;

    public Currency (String fromCurrencyCode,
                     String fromCurrencyName,
                     String toCurrencyCode,
                     String toCurrencyName,
                     Double exchangeRate,
                     LocalDateTime lastRefreshed,
                     String timeZone){
        this.fromCurrencyCode = fromCurrencyCode;
        this.fromCurrencyName = fromCurrencyName;
        this.toCurrencyCode = toCurrencyCode;
        this.toCurrencyName = toCurrencyName;
        this.exchangeRate = exchangeRate;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public String getFromCurrencyName() {
        return fromCurrencyName;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public String getToCurrencyName() {
        return toCurrencyName;
    }

    public Double getExchangeRate() { return exchangeRate; }

    public String getExchangeRateFormatted() {
        return CurrencyFormatter.format(toCurrencyCode, exchangeRate);
    }

    public LocalDateTime getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
