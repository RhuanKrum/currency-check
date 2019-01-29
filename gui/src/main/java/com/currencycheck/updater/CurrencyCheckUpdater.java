package com.currencycheck.updater;

import com.currencycheck.factory.CurrencyCheckFactory;
import com.currencycheck.model.Currency;
import com.currencycheck.service.CurrencyCheckServiceI;
import com.currencycheck.util.CurrencyCode;

public class CurrencyCheckUpdater implements Runnable {

    private final CurrencyCheckFactory factory;
    private final CurrencyCheckServiceI service;

    private Currency currency;

    public CurrencyCheckUpdater(){
        factory = new CurrencyCheckFactory();
        service = factory.getCurrencyCheckService();
    }

    @Override
    public void run() {
        try{
            currency = service.getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL);
        } catch (Exception e){
            e.printStackTrace();
            // TODO: Create some validation for repetitive failures, and add some simple text to the UI (exceptionPane?)
        }
    }

    public Currency getCurrency(){
        return currency;
    }
}
