package de.bcxp.challenge.parsing;
import de.bcxp.challenge.model.Country;
import de.bcxp.challenge.model.WeatherDay;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryParserTest {

    private final CountryParser parser = new CountryParser();

    @Test
    void parse_validRows_returnsCountries() {
        List<String[]> rows = List.of(
            new String[]{"Name", "Capital", "Accession", "Population", "Area (km²)", "GDP (US$ M)", "HDI", "MEPs"},
            new String[]{"Austria", "Vienna", "1995", "8926000", "83855", "447718", "0.922", "19"}
        );

        List<Country> result = parser.parse(rows);

        assertEquals(1, result.size());
        assertEquals("Austria", result.get(0).getName());
        assertEquals(8926000.0, result.get(0).getPopulation());
        assertEquals(83855.0, result.get(0).getArea());
    }

    @Test
    void parse_europeanFormattedPopulation_parsesCorrectly() {
        List<String[]> rows = List.of(
            new String[]{"Name", "Capital", "Accession", "Population", "Area (km²)", "GDP (US$ M)", "HDI", "MEPs"},
            new String[]{"Croatia", "Zagreb", "2013", "4.036.355,00", "56594", "60702", "0.851", "12"}
        );

        List<Country> result = parser.parse(rows);

        assertEquals(4036355.0, result.get(0).getPopulation());
    }

    @Test
    void parse_invalidRow_skipsAndLogs() {
        List<String[]> rows = List.of(
            new String[]{"Name", "Capital", "Accession", "Population", "Area (km²)", "GDP (US$ M)", "HDI", "MEPs"},
            new String[]{"Nowhere", "Nowhere City", "2000", "not-a-number", "100", "0", "0.5", "0"}
        );

        List<Country> result = parser.parse(rows);

        assertTrue(result.isEmpty());
    }

    @Test
    void parse_nullRows_throws() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }
    @Test
    void parse_missingHeader_throws() {
        List<String[]> rows = List.<String[]>of(new String[]{"Foo", "Bar"});
        assertThrows(IllegalArgumentException.class, () -> parser.parse(rows));
    }
    @Test
    void parse_zeroArea_skipsRow() {
        List<String[]> rows = List.of(
            new String[]{"Name", "Capital", "Accession", "Population", "Area (km²)", "GDP (US$ M)", "HDI", "MEPs"},
            new String[]{"Nowhere", "City", "2000", "1000", "0", "0", "0.5", "0"}
        );
        assertTrue(parser.parse(rows).isEmpty());
    }
    @Test
    void parse_emptyRows_returnsEmptyList() {
        assertTrue(parser.parse(List.of()).isEmpty());
    }

    @Test
    void parse_renamedAreaColumn_throwsClearError() {
        List<String[]> rows = List.of(
            new String[]{"Name", "Population", "Area (m²)"},  // not "Area (km²)"
            new String[]{"Somewhere", "1000", "100"}
        );
        assertThrows(IllegalArgumentException.class, () -> parser.parse(rows));
    }
}
