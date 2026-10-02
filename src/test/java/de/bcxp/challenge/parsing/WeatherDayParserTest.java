package de.bcxp.challenge.parsing;

import de.bcxp.challenge.model.WeatherDay;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDayParserTest {

    private final WeatherDayParser parser = new WeatherDayParser();

    @Test
    void parsesValidRowCorrectly() {
        List<String[]> rows = List.<String[]>of(
            new String[]{"Day", "MxT", "MnT"},
            new String[]{"14", "61", "59"}
        );
        List<WeatherDay> result = parser.parse(rows);
        assertEquals("14", result.get(0).getDay());
        assertEquals(61, result.get(0).getMaxTemp());
        assertEquals(59, result.get(0).getMinTemp());
    }

    @Test
    void skipsInvalidRow() {
        List<String[]> rows = List.<String[]>of(
            new String[]{"Day", "MxT", "MnT"},
            new String[]{"1", "notANumber", "60"},
            new String[]{"2", "79", "75"}
        );
        List<WeatherDay> result = parser.parse(rows);
        assertEquals(1, result.size());
        assertEquals("2", result.get(0).getDay());
    }

    @Test
    void returnsEmptyListForEmptyInput() {
        List<WeatherDay> result = parser.parse(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void throwsExceptionWhenNoHeader() {
        List<String[]> rows = List.<String[]>of(
            new String[]{"1", "88", "59"},
            new String[]{"2", "79", "63"}
        );
        assertThrows(IllegalArgumentException.class, () -> parser.parse(rows));
    }

    @Test
    void parsesCorrectlyWhenColumnsAreInDifferentOrder() {
        List<String[]> rows = List.<String[]>of(
            new String[]{"MnT", "Day", "MxT"},
            new String[]{"59", "14", "61"}
        );
        List<WeatherDay> result = parser.parse(rows);
        assertEquals("14", result.get(0).getDay());
        assertEquals(61, result.get(0).getMaxTemp());
        assertEquals(59, result.get(0).getMinTemp());
    }

    @Test
    void throwsExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }

    @Test
    void parse_invertedTemps_skipsRow() {
        // MnT (59) > MxT (50) — physically invalid
        List<String[]> rows = List.of(
            new String[]{"Day","MxT","MnT"},
            new String[]{"1","50","59"}
        );
        assertTrue(parser.parse(rows).isEmpty());
    }
    @Test
    void parse_emptyRows_returnsEmptyList() {
        assertTrue(parser.parse(List.of()).isEmpty());
    }

    @Test
    void parse_whitespacePaddedTemperatures_stillParses() {
        List<String[]> rows = List.of(
            new String[]{"Day", "MxT", "MnT"},
            new String[]{"1", " 88", "59 "}
        );

        List<WeatherDay> result = parser.parse(rows);

        assertEquals(1, result.size());
        assertEquals(88, result.get(0).getMaxTemp());
        assertEquals(59, result.get(0).getMinTemp());
    }
    @Test
    void throwsExceptionWhenMxTColumnMissing() {
        List<String[]> rows = List.<String[]>of(
            new String[]{"Day", "Max", "MnT"},  // "MxT" renamed to "Max"
            new String[]{"1", "88", "59"}
        );
        assertThrows(IllegalArgumentException.class, () -> parser.parse(rows));
    }
}