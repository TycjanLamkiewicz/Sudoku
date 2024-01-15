package org.example;

import org.example.exceptions.ExistenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExistenceExceptionTest {

    @Test
    void existenceExceptionTest() {
        String message = "Test message";

        ExistenceException existenceException = new ExistenceException(message);

        assertEquals(message, existenceException.getMessage());
    }
}