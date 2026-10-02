package de.bcxp.challenge.reading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CsvFileReader implements DataReader{

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
    List<String[]> rows = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            rows.add(line.split(Pattern.quote(delimiter)));
        }
    } catch (IOException e) {
        throw new IllegalArgumentException("Could not read file: " + filePath,e);
    }
    return rows;
    }
}