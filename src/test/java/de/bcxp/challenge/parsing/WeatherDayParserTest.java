package de.bcxp.challenge.parsing;

import de.bcxp.challenge.model.WeatherDay;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDayParserTest {

    private final WeatherDayParser parser = new WeatherDayParser();

    @Test
    void parsesValidRowCorrectly() {
        List<String[]> rows = List.<String[]>of(new String[]{"14", "61", "59"});
        List<WeatherDay> result = parser.parse(rows);
        assertEquals("14", result.get(0).getDay());
        assertEquals(61, result.get(0).getMaxTemp());
        assertEquals(59, result.get(0).getMinTemp());
    }

    @Test
    void skipsInvalidRow() {
        List<String[]> rows = List.of(
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
    void throwsExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }
}