package com.currencycheck;

import com.currencycheck.factory.CurrencyCheckFactory;
import com.currencycheck.model.Currency;
import com.currencycheck.service.CurrencyCheckServiceI;
import com.currencycheck.util.CurrencyCode;

public class CurrencyCheckUpdater implements Runnable {

    private final CurrencyCheckFactory factory;
    private final CurrencyCheckServiceI service;

    private final CurrencyCode fromCurrency;
    private final CurrencyCode toCurrency;

    private Currency currency;

    public CurrencyCheckUpdater(CurrencyCode fromCurrency, CurrencyCode toCurrency){
        this.factory = new CurrencyCheckFactory();
        this.service = factory.getCurrencyCheckService();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    @Override
    public void run() {
        try{
            currency = service.getCurrencyExchangeRate(fromCurrency, toCurrency);
        } catch (Exception e){
            e.printStackTrace();
            // TODO: Create some validation for repetitive failures, and add some simple text to the UI (exceptionPane?)
        }
    }

    public Currency getCurrency(){
        return currency;
    }
}
