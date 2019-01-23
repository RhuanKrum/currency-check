package com.currencycheck.exception;

public class MessageFormatException extends RuntimeException {

    public MessageFormatException(String errorMessage){
        super(errorMessage);
    }
}
