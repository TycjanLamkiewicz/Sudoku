package org.example;

import org.example.exceptions.ValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ValueExceptionTest {

    @Test
    void valueExceptionTest() {
        String message = "Test message";

        ValueException valueException = new ValueException(message);

        assertEquals(message, valueException.getMessage());
    }
}