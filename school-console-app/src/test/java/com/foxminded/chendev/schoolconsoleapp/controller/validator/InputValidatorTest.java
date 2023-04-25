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

        long notValidLong = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(notValidLong);
        });
    }

    @Test
    void validateValueShouldIllegalArgumentExceptionWhenNumberIsZero() {

        long notValidLong = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(notValidLong);
        });
    }

    @Test
    void validateShouldNotThrowExceptionWhenInputValidNumber() {

        long validLong = 1;

        assertDoesNotThrow(() -> {
            validator.validate(validLong);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputEmpty() {

        String emptyString = "";

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(emptyString);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputNull() {

        String nullString = null;

        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(nullString);
        });
    }

    @Test
    void validateShouldNotThrowIllegalArgumentWhenInputValidString() {

        String validString = "String";

        assertDoesNotThrow(() -> {
            validator.validate(validString);
        });
    }
}
