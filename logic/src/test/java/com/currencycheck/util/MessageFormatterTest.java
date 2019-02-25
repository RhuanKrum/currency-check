package com.currencycheck.util;

import com.currencycheck.exception.MessageFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: Document class
 */
public class MessageFormatterTest {

    @Test
    public void formatOnNullMessageTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format(null, "Test"), "Error");
    }

    @Test
    public void formatOnNoMessageParametersTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test", "Test"), "Error");
    }

    @Test
    public void formatOnNullValuesTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test {}", null), "Error");
    }

    @Test
    public void formatOnEmptyValuesTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test {}", new String[1]));
    }

    @Test
    public void formatOnLessValuesTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test Two Parameters {} {}", "One"));
    }

    @Test
    public void formatOnMoreValuesTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test Two Parameters {} {}", "One", "Two", "Three"));
    }

    @Test
    public void formatOnSomeValuesAreNullTest(){
        assertThrows(MessageFormatException.class, () -> MessageFormatter.format("Test Three Parameters {} {} {}", "One", null, "Three"));
    }

    @Test
    public void formatOnOneParameter(){
        assertEquals(MessageFormatter.format("Test One Parameter {}", "One"), "Test One Parameter One");
    }

    @Test
    public void formatOnTwoParameters(){
        assertEquals(MessageFormatter.format("Test Two Parameters {} {}", "One", "Two"), "Test Two Parameters One Two");
    }

    @Test
    public void formatOnThreeParameters(){
        assertEquals(MessageFormatter.format("Test Three Parameters {} {} {}", "One", "Two", "Three"), "Test Three Parameters One Two Three");
    }

    @Test
    public void formatOnDollarParameter(){
        assertDoesNotThrow(() -> MessageFormatter.format("Test Dollar Parameter {}", "\\$2.00"), "Error");
    }
}
