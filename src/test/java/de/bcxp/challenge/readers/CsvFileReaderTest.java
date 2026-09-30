package de.bcxp.challenge.readers;

import org.junit.jupiter.api.Test;

import de.bcxp.challenge.reading.CsvFileReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvFileReaderTest {
    private final CsvFileReader reader = new CsvFileReader();

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
        List<String[]> rows = reader.read("src\\main\\resources\\de\\bcxp\\challenge\\weather.csv");
        assertEquals(31, rows.size()); 
    }

    @Test
    void returnsCorrectNumberOfColumns() {
        List<String[]> rows = reader.read("src\\main\\resources\\de\\bcxp\\challenge\\weather.csv");
        assertEquals(14, rows.get(0).length);
    }
}
