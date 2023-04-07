package com.foxminded.chendev.schoolconsoleapp.reader;

public class FileReaderException extends RuntimeException {

    public FileReaderException() {
        super();
    }

    public FileReaderException(String message) {
        super(message);
    }

    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileReaderException(Throwable cause) {
        super(cause);
    }
}
