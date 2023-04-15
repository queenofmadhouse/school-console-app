package com.foxminded.chendev.schoolconsoleapp.controller.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
    }

    @Test
    void validateValueShouldThrowIllegalArgumentExceptionWhenNumberSmallerThenZero() {

        long value = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateValueShouldIllegalArgumentExceptionWhenNumberIsZero() {

        long value = 0;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldNotThrowExceptionWhenInputValidNumber() {

        long value = 1;

        Assertions.assertDoesNotThrow(() -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputEmpty() {

        String value = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionWhenInputNull() {

        String value = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(value);
        });
    }

    @Test
    void validateShouldNotThrowIllegalArgumentWhenInputValidString() {

        String value = "String";

        Assertions.assertDoesNotThrow(() -> {
            validator.validate(value);
        });
    }
}
