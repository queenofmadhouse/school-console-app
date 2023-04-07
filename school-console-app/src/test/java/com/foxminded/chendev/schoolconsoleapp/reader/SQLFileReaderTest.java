package com.foxminded.chendev.schoolconsoleapp.reader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class SQLFileReaderTest {

    private SQLFileReader fileReader;
    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        fileReader = new SQLFileReader();
        String tempDir = getClass().getClassLoader().getResource("").getPath();
        tempFile = Files.createTempFile(Paths.get(tempDir), "test_sql_", ".sql");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void readFileShouldReturnFileContentWhenFileExists() throws IOException {

        String sqlContent = "CREATE TABLE test_table (id INTEGER PRIMARY KEY, name TEXT);";
        Files.write(tempFile, sqlContent.getBytes(StandardCharsets.UTF_8));

        String classpathResource = tempFile.getFileName().toString();
        String result = fileReader.readFile(classpathResource);

        assertEquals(sqlContent, result);
    }

    @Test
    void testReadFileWhenFileNotFound() {

        String filePath = "nonexistentfile.sql";

        assertThrows(FileReaderException.class, () -> fileReader.readFile(filePath));
    }
}
