package de.bcxp.challenge.reading;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvFileReader implements DataReader {

    private final String delimiter;

    public CsvFileReader() {
        this(",");
    }

    public CsvFileReader(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<String[]> read(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must not be null");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        CSVFormat format = CSVFormat.DEFAULT.builder().setDelimiter(delimiter).build();

        List<String[]> rows = new ArrayList<>();
        try (Reader in = new FileReader(file); CSVParser parser = new CSVParser(in, format)) {
            for (CSVRecord record : parser) {
                String[] row = new String[record.size()];
                for (int i = 0; i < record.size(); i++) {
                    row[i] = record.get(i);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file: " + filePath, e);
        }
        return rows;
    }
}