package com.currencycheck.util;

// TODO: Use AlphaAdvantage Currency CSV file: https://www.alphavantage.co/physical_currency_list/
public enum CurrencyCode {
    CAD ("CAD"),
    BRL ("BRL");

    private String currencyCode;

    CurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public String getValue(){
        return this.currencyCode;
    }
}
