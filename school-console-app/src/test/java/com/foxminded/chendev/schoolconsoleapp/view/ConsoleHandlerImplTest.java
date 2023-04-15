package com.foxminded.chendev.schoolconsoleapp.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleHandlerImplTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private ConsoleHandler consoleHandler;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void readUserInputString_shouldReturnCorrectString() {

        String input = "Hello, world!\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        consoleHandler = new ConsoleHandlerImpl(scanner);

        String actual = consoleHandler.readUserInputString();

        assertEquals(input.trim(), actual);
    }

    @Test
    void readUserInputNumber_shouldReturnCorrectNumber() {

        long expected = 42;
        String input = expected + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        consoleHandler = new ConsoleHandlerImpl(scanner);

        long actual = consoleHandler.readUserInputNumber();

        assertEquals(expected, actual);
    }

    @Test
    void printMessage_shouldPrintCorrectMessage() {

        String input = "Hello, world!";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        consoleHandler = new ConsoleHandlerImpl(scanner);

        consoleHandler.printMessage(input);
        String printedMessage = outputStreamCaptor.toString().trim();

        assertEquals(input, printedMessage);
    }
}
