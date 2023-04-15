package com.foxminded.chendev.schoolconsoleapp.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DataBaseRuntimeExceptionTest {

    @Test
    void shouldCreateEmptyDataBaseRuntimeException() {

        DataBaseRuntimeException exception = new DataBaseRuntimeException();

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateDataBaseRuntimeExceptionWithMessage() {

        String message = "Test message";

        DataBaseRuntimeException exception = new DataBaseRuntimeException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateDataBaseRuntimeExceptionWithCause() {

        Throwable cause = new RuntimeException("Test cause");

        DataBaseRuntimeException exception = new DataBaseRuntimeException(cause);

        assertEquals("java.lang.RuntimeException: Test cause", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldCreateDataBaseRuntimeExceptionWithMessageAndCause() {

        String message = "Test message";
        Throwable cause = new RuntimeException("Test cause");

        DataBaseRuntimeException exception = new DataBaseRuntimeException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
