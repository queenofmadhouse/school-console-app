package com.foxminded.chendev.schoolconsoleapp.view;

public interface ConsoleHandler {

    void printMessage(String message);

    String readUserInputString();

    long readUserInputNumber();
}
