package de.bcxp.challenge.analysis;

import de.bcxp.challenge.analysis.Analyzer;
import de.bcxp.challenge.model.WeatherDay;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerTest {

    private final Analyzer<WeatherDay> analyzer = new Analyzer<>();

    @Test
    void findsEntryWithSmallestSpread() {
        List<WeatherDay> days = List.of(
            new WeatherDay("14", 61, 59),  // spread 2
            new WeatherDay("2", 79, 63),   // spread 16
            new WeatherDay("9", 86, 32)    // spread 54
        );
        Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
        assertEquals("14", analyzer.findBy(days, bySpread).getDay());
    }

    @Test
    void findsEntryWithLargestSpread() {
        List<WeatherDay> days = List.of(
            new WeatherDay("14", 61, 59),  // spread 2
            new WeatherDay("2", 79, 63),   // spread 16
            new WeatherDay("9", 86, 32)    // spread 54
        );
        Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
        assertEquals("9", analyzer.findBy(days, bySpread.reversed()).getDay());
    }

    @Test
    void returnFirstWhenTie() {
        List<WeatherDay> days = List.of(
            new WeatherDay("1", 80, 70),  // spread 10
            new WeatherDay("2", 90, 80)   // spread 10
        );
        Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
        assertEquals("1", analyzer.findBy(days, bySpread).getDay());
    }

    @Test
    void throwsExceptionForEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.findBy(List.of(), Comparator.comparingInt(d -> d.getMaxTemp())));
    }

    @Test
    void throwsExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.findBy(null, Comparator.comparingInt(d -> d.getMaxTemp())));
    }
}