package de.bcxp.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.bcxp.challenge.analysis.Analyzer;
import de.bcxp.challenge.model.WeatherDay;
import de.bcxp.challenge.parsing.WeatherDayParser;
import de.bcxp.challenge.reading.CsvFileReader;
import de.bcxp.challenge.reading.DataReader;
import de.bcxp.challenge.parsing.Parser;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.List;

/**
 * Example JUnit 5 test case.
 */
class AppTest {

    private String successLabel = "not successful";

    @BeforeEach
    void setUp() {
        successLabel = "successful";
    }

    @Test
    void aPointlessTest() {
        assertEquals("successful", successLabel, "My expectations were not met");
    }

    @Test
      void dayWithSmallestTemperatureSpread() {
          DataReader reader = new CsvFileReader();
          Parser<WeatherDay> parser = new WeatherDayParser();
          Analyzer<WeatherDay> analyzer = new Analyzer<>();

          List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.csv");
          List<WeatherDay> days = parser.parse(rows);
          Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
          WeatherDay result = analyzer.findBy(days, bySpread);

          assertEquals("14", result.getDay());
      }

}