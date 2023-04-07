package com.foxminded.chendev.schoolconsoleapp.view;

import java.util.Scanner;

public class ConsoleHandlerImpl implements ConsoleHandler {

    private Scanner scanner;

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
