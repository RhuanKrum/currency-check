package com.currencycheck.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyFormatter {

    public static final String format(String countryCode, Double currencyValue){
        if(countryCode.length() >= 3){
            countryCode = countryCode.substring(0, 2);
        }

        Locale locale = new Locale("", countryCode);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        return numberFormat.format(currencyValue);
    }
}
