package de.bcxp.challenge.parsing;

import de.bcxp.challenge.model.Country;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryParser implements Parser<Country> {

    private static final Logger logger = Logger.getLogger(CountryParser.class.getName());

    public List<Country> parse(List<String[]> rows) {
        if (rows == null) {
            throw new IllegalArgumentException("Rows must not be null");
        }
        List<Country> result = new ArrayList<>();
        int nameInd = -1, popInd = -1, areaInd = -1;

        for (String[] row : rows) {
            if (nameInd == -1) {
                nameInd = Arrays.asList(row).indexOf("Name");
                popInd = Arrays.asList(row).indexOf("Population");
                areaInd = Arrays.asList(row).indexOf("Area (km²)");
                if (nameInd == -1 || popInd == -1 || areaInd == -1) {
                    throw new IllegalArgumentException("CSV file must have a header row with Name, Population, Area (km²) columns");
                }
                continue;
            }
            try {
                double population = parseGerEUNumber(row[popInd]);
                double area = parseGerEUNumber(row[areaInd]);
                if (area <= 0) {
                    throw new NumberFormatException("Area must be positive");
                }
                result.add(new Country(row[nameInd], population, area));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.warning("Skipping invalid row: " + Arrays.toString(row));
            }
        }

        return result;
    }

    private double parseGerEUNumber(String raw) {
        String normalized = raw.strip().replace(".", "").replace(",", ".");
        return Double.parseDouble(normalized);
    }
}