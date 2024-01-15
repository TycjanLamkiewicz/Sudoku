package org.example;

import org.example.exceptions.CloneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CloneExceptionTest {

    @Test
    void cloneExceptionTest() {
        String message = "Test message";

        CloneException cloneException = new CloneException(message);

        assertEquals(message, cloneException.getMessage());
    }
}