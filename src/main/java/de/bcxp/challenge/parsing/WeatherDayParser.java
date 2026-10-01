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
        int dayInd = -1, mxTInd = -1, mnTInd = -1;
        
        for (String[] row : rows) {
            if (dayInd == -1) {
                dayInd = Arrays.asList(row).indexOf("Day");
                mxTInd = Arrays.asList(row).indexOf("MxT");
                mnTInd = Arrays.asList(row).indexOf("MnT");
                if (dayInd == -1) {
                    throw new IllegalArgumentException("CSV file must have a header row with Day, MxT, MnT columns");
                }
                continue;
            }
            try {
                result.add(new WeatherDay(row[dayInd], Integer.parseInt(row[mxTInd]), Integer.parseInt(row[mnTInd])));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.warning("Skipping invalid row: " + Arrays.toString(row));
            }
        }
        return result;
    }
}
   