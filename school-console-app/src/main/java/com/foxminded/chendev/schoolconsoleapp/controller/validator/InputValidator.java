package com.foxminded.chendev.schoolconsoleapp.controller.validator;

import org.springframework.stereotype.Component;

@Component
public class InputValidator implements Validator {

    @Override
    public void validate(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("input is empty");
        }
    }

    @Override
    public void validate(long value) {

        if (value <= 0) {
            throw new IllegalArgumentException("value can't be <= 0");
        }
    }
}
