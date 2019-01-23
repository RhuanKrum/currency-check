package com.currencycheck.util;

public enum CurrencyCode {
    CAD ("CAD"),
    BRL ("BRL");

    private String currencyCode;

    CurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode(){
        return this.currencyCode;
    }
}
