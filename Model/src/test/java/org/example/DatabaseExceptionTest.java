package org.example;

import org.example.exceptions.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {

    @Test
    void databaseExceptionTest() {
        String message = "Test message";

        DatabaseException databaseException = new DatabaseException(message);

        assertEquals(message, databaseException.getMessage());
    }
}