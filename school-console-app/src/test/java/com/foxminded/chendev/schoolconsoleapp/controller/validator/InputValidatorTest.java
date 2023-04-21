package com.foxminded.chendev.schoolconsoleapp.controller.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
    }

    @Test
    void validateValueShouldThrowIllegalArgumentExceptionWhenNumberSmallerThenZero() {

        long value = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateValueShouldIllegalArgumentExceptionWhenNumberIsZero() {

        long value = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldNotThrowExceptionWhenInputValidNumber() {

        long value = 1;

        assertDoesNotThrow(() -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputEmpty() {

        String value = "";

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputNull() {

        String value = null;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldNotThrowIllegalArgumentWhenInputValidString() {

        String value = "String";

        assertDoesNotThrow(() -> {
            validator.validate(value);
        });
    }
}
