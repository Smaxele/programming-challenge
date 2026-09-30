package de.bcxp.challenge;

import de.bcxp.challenge.parsing.Parser;

import java.util.Comparator;
import java.util.List;

import de.bcxp.challenge.analysis.Analyzer;
import de.bcxp.challenge.model.WeatherDay;
import de.bcxp.challenge.parsing.WeatherDayParser;
import de.bcxp.challenge.reading.CsvFileReader;
import de.bcxp.challenge.reading.DataReader;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Your preparation code …
        DataReader reader = new CsvFileReader();
        Parser<WeatherDay> parser = new WeatherDayParser();
        Analyzer<WeatherDay> analyzer = new Analyzer<>();
        
        List<String[]> rows = reader.read("src/main/resources/de/bcxp/challenge/weather.csv");
        List<WeatherDay> days = parser.parse(rows);
        Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
        WeatherDay dayWithSmallestTempSpread = analyzer.findBy(days, bySpread);

        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread.getDay());

        String countryWithHighestPopulationDensity = "Some country"; // Your population density analysis function call …
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
