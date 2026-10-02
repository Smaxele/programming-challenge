package de.bcxp.challenge.reading;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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

    @Test
    void throwsExceptionForEmptyArray(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("empty.json");
        Files.writeString(file, "[]");

        assertThrows(IllegalArgumentException.class, () -> reader.read(file.toString()));
    }

    @Test
    void throwsExceptionForInconsistentColumns(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("inconsistent.json");
        Files.writeString(file, "[{\"Day\": \"1\", \"MxT\": \"88\", \"MnT\": \"59\"},"
            + "{\"Day\": \"2\", \"MxT\": \"79\"}]");

        assertThrows(IllegalArgumentException.class, () -> reader.read(file.toString()));
    }

    @Test
    void alignsColumnsByKeyRegardlessOfJsonFieldOrder(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("reordered.json");
        Files.writeString(file, "[{\"Day\": \"1\", \"MxT\": \"88\", \"MnT\": \"59\"},"
            + "{\"MnT\": \"63\", \"Day\": \"2\", \"MxT\": \"79\"}]");

        List<String[]> rows = reader.read(file.toString());

        assertArrayEquals(new String[]{"Day", "MxT", "MnT"}, rows.get(0));
        assertArrayEquals(new String[]{"2", "79", "63"}, rows.get(2));
    }

    @Test
    void throwsExceptionForMalformedJson(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("malformed.json");
        Files.writeString(file, "{not valid json");

        assertThrows(IllegalArgumentException.class, () -> reader.read(file.toString()));
    }
}
