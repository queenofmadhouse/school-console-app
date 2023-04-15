package com.foxminded.chendev.schoolconsoleapp.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleHandlerImpl implements ConsoleHandler {

    private final Scanner scanner;

    public ConsoleHandlerImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String readUserInputString() {
        return scanner.nextLine();
    }

    @Override
    public long readUserInputNumber() {

        long value = scanner.nextLong();
        scanner.nextLine();

        return value;
    }
}
