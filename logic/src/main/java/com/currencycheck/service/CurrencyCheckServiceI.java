package com.currencycheck.service;

import com.currencycheck.model.Currency;
import com.currencycheck.util.CurrencyCode;

import java.io.IOException;

/**
 * TODO: Document class
 */
public interface CurrencyCheckServiceI {

    Currency getCurrencyExchangeRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) throws IOException;
}
