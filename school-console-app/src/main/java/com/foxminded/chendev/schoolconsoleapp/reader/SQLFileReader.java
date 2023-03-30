package com.foxminded.chendev.schoolconsoleapp.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SQLFileReader implements FileReader{

    @Override
    public String readFile(String filePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        String sqlScript = "";

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                sqlScript = reader.lines().collect(Collectors.joining(" "));
            } catch (IOException e) {
                throw new FileReaderException("Не удалось прочитать файл SQL скрипта: " + e.getMessage());
            }
        } else {
            throw new FileReaderException("Не удалось найти файл SQL скрипта: " + filePath);
        }

        return sqlScript;
    }
}