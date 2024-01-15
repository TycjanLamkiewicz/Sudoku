package org.example;

import org.example.exceptions.DaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DaoExceptionTest {

    @Test
    void daoExceptionTest() {
        Throwable cause = mock(Throwable.class);

        DaoException daoException = new DaoException(cause);

        assertEquals(cause, daoException.getCause());
    }
}