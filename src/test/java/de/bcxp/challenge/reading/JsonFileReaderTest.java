package de.bcxp.challenge.reading;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class JsonFileReaderTest {
    private final JsonFileReader reader = new JsonFileReader();

    @Test
    void throwsExceptionForNullPath() {
        assertThrows(IllegalArgumentException.class, () -> reader.read(null));
    }

    @Test
    void throwsExceptionForNonExistentFile() {
        assertThrows(IllegalArgumentException.class, () -> reader.read("nonexistent.csv"));
    }
    @Test
    void returnsCorrectNumberOfRows() {
        List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.json");
        assertEquals(4, rows.size()); 
    }
    @Test
    void returnsCorrectNumberOfColumns() {
        List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.json");
        assertEquals(3, rows.get(0).length);
    }

    
}
