package org.example;

import org.example.exceptions.FileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FileExceptionTest {

    @Test
    void fileExceptionTest() {
        Throwable cause = mock(Throwable.class);

        FileException fileException = new FileException(cause);

        assertEquals(cause, fileException.getCause());
    }
}