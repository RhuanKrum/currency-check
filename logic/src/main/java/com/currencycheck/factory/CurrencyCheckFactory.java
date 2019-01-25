package com.currencycheck.factory;

import com.currencycheck.service.CurrencyCheckService;
import com.currencycheck.service.CurrencyCheckServiceI;

/**
 * TODO: Document class
 */
public class CurrencyCheckFactory {

    /**
     * TODO: Document method
     * @return
     */
    public CurrencyCheckServiceI getCurrencyCheckService(){
        return new CurrencyCheckService();
    }
}
