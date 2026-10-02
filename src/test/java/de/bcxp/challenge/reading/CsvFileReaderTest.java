package de.bcxp.challenge.reading;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
        List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.csv");
        assertEquals(31, rows.size()); 
    }
    @Test
    void returnsCorrectNumberOfColumns() {
        List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.csv");
        assertEquals(14, rows.get(0).length);
    }

    @Test
    void read_withSemicolonDelimiter_splitsFieldsCorrectly(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.csv");
        Files.writeString(file, "a;b;c\n1;2;3");

        CsvFileReader reader = new CsvFileReader(";");
        List<String[]> rows = reader.read(file.toString());

        assertArrayEquals(new String[]{"a", "b", "c"}, rows.get(0));
        assertArrayEquals(new String[]{"1", "2", "3"}, rows.get(1));
    }

    @Test
    void read_defaultConstructor_splitsOnComma(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.csv");
        Files.writeString(file, "a,b,c\n1,2,3");

        CsvFileReader reader = new CsvFileReader();
        List<String[]> rows = reader.read(file.toString());

        assertArrayEquals(new String[]{"a", "b", "c"}, rows.get(0));
    }

    @Test
    void read_regexSpecialDelimiter_splitsLiterally(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.csv");
        Files.writeString(file, "a.b.c");
        CsvFileReader reader = new CsvFileReader(".");
        List<String[]> rows = reader.read(file.toString());
        assertArrayEquals(new String[]{"a", "b", "c"}, rows.get(0));
    }

    @Test
    void read_quotedFieldContainingDelimiter_staysOneField(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.csv");
        Files.writeString(file, "Name,Age\n\"Doe, John\",42");

        List<String[]> rows = reader.read(file.toString());

        assertArrayEquals(new String[]{"Name", "Age"}, rows.get(0));
        assertArrayEquals(new String[]{"Doe, John", "42"}, rows.get(1));
    }

}
