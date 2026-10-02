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
                if (Integer.parseInt(row[mxTInd].strip()) < Integer.parseInt(row[mnTInd].strip())) {
                    logger.warning("Skipping invalid row, maximum is smaller than minimum: " + Arrays.toString(row));
                    continue;
                }
                result.add(new WeatherDay(row[dayInd], Integer.parseInt(row[mxTInd].strip()), Integer.parseInt(row[mnTInd].strip())));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.warning("Skipping invalid row: " + Arrays.toString(row));
            }
        }
        return result;
    }
}
   