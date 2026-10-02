package de.bcxp.challenge.parsing;

import de.bcxp.challenge.model.WeatherDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.List;


public class WeatherDayParser implements Parser<WeatherDay>{

    private static final Logger logger = Logger.getLogger(WeatherDayParser.class.getName());

    public List<WeatherDay> parse(List<String[]> rows) {
        if (rows == null) {
              throw new IllegalArgumentException("Rows must not be null");
          }
        List<WeatherDay> result = new ArrayList<>();
        int dayIndex = -1, maxTempIndex = -1, minTempIndex = -1;

        for (String[] row : rows) {
            if (dayIndex == -1) {
                dayIndex = Arrays.asList(row).indexOf("Day");
                maxTempIndex = Arrays.asList(row).indexOf("MxT");
                minTempIndex = Arrays.asList(row).indexOf("MnT");
                if (dayIndex == -1 || maxTempIndex == -1 || minTempIndex == -1) {
                    throw new IllegalArgumentException("CSV file must have a header row with Day, MxT, MnT columns");
                }
                continue;
            }
            try {
                if (Integer.parseInt(row[maxTempIndex].strip()) < Integer.parseInt(row[minTempIndex].strip())) {
                    logger.warning("Skipping invalid row, maximum is smaller than minimum: " + Arrays.toString(row));
                    continue;
                }
                result.add(new WeatherDay(row[dayIndex], Integer.parseInt(row[maxTempIndex].strip()), Integer.parseInt(row[minTempIndex].strip())));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.warning("Skipping invalid row: " + Arrays.toString(row));
            }
        }
        return result;
    }
}
   