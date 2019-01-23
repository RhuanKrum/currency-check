package com.currencycheck.util;

import com.currencycheck.exception.MessageFormatException;

import java.util.Arrays;

public final class MessageFormatter {

    private static final String REPLACEABLE_SYMBOL = "\\{\\}";

    public static final String format(String message, Object... values){
        validateMessageAndValues(message, values);

        String formattedMessage = message;
        for(Object value : values){
            formattedMessage.replaceFirst(REPLACEABLE_SYMBOL, value.toString());
        }

        return formattedMessage;
    }

    private static final void validateMessageAndValues(String message, Object... values){
        if(message == null)
            throw new MessageFormatException("Message is null");
        if(values == null)
            throw new MessageFormatException("Values is null");
        if(Arrays.stream(values).anyMatch(v -> v == null))
            throw new MessageFormatException("One of the passed Values is null");
        if(message.split(REPLACEABLE_SYMBOL, -1).length-1 != values.length)
            throw new MessageFormatException("Message parameters and values do not match");
    }
}
