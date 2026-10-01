package de.bcxp.challenge;

import de.bcxp.challenge.parsing.CountryParser;
import de.bcxp.challenge.parsing.Parser;

import java.util.Comparator;
import java.util.List;

import de.bcxp.challenge.analysis.Analyzer;
import de.bcxp.challenge.model.Country;
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

        DataReader weatherReader = new CsvFileReader(",");
        Parser<WeatherDay> weatherParser = new WeatherDayParser();
        Analyzer<WeatherDay> weatherAnalyzer = new Analyzer<>();

        List<String[]> weatherRows = weatherReader.read("src/main/resources/de/bcxp/challenge/weather.csv");
        List<WeatherDay> days = weatherParser.parse(weatherRows);
        Comparator<WeatherDay> bySpread = Comparator.comparingInt(d -> d.getMaxTemp() - d.getMinTemp());
        WeatherDay dayWithSmallestTempSpread = weatherAnalyzer.findBy(days, bySpread);

        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread.getDay());

        DataReader countryReader = new CsvFileReader(";");
        Parser<Country> countryParser = new CountryParser();
        Analyzer<Country> countryAnalyzer = new Analyzer<>();

        List<String[]> countryRows = countryReader.read("src/main/resources/de/bcxp/challenge/countries.csv");
        List<Country> countries = countryParser.parse(countryRows);
        Comparator<Country> byDensityDescending = Comparator.comparingDouble(Country::getDensity).reversed();
        Country countryWithHighestDensity = countryAnalyzer.findBy(countries, byDensityDescending);

        System.out.printf("Country with highest population density: %s%n", countryWithHighestDensity.getName());
    }
}
