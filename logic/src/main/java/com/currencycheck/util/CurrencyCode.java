package com.currencycheck.util;

// TODO: Use AlphaAdvantage Currency CSV file: https://www.alphavantage.co/physical_currency_list/
public enum CurrencyCode {
    AUD ("AUD", "Australian Dollar"),
    BRL ("BRL", "Brazilian Real"),
    CAD ("CAD", "Canadian Dollar"),
    CNY ("CNY", "Chinese Yuan"),
    EUR ("EUR", "Euro"),
    USD ("USD", "US Dollar");

    private String currencyCode;
    private String currencyName;

    CurrencyCode(String currencyCode, String currencyName){
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    public String getCode(){
        return this.currencyCode;
    }
    public String getName(){
        return this.currencyName;
    }
}
