package de.bcxp.challenge.reading;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class JsonFileReader implements DataReader {

    private static final Type RECORDS_TYPE = new TypeToken<List<Map<String, String>>>() {}.getType();

    @Override
    public List<String[]> read(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must not be null");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        List<Map<String, String>> records;
        try (Reader reader = new FileReader(file)) {
            records = new Gson().fromJson(reader, RECORDS_TYPE);
        } catch (IOException | JsonParseException | IllegalStateException e) {
            throw new IllegalArgumentException("Could not read file: " + filePath, e);
        }

        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("JSON file contains no records: " + filePath);
        }

        List<String> header = new ArrayList<>(records.get(0).keySet());
        List<String[]> rows = new ArrayList<>();
        rows.add(header.toArray(new String[0]));

        for (Map<String, String> record : records) {
            if (!record.keySet().equals(records.get(0).keySet())) {
                throw new IllegalArgumentException(
                    "Inconsistent JSON record columns in " + filePath
                        + ". Expected " + header + " but found " + record.keySet());
            }
            String[] row = new String[header.size()];
            for (int i = 0; i < header.size(); i++) {
                row[i] = record.get(header.get(i));
            }
            rows.add(row);
        }

        return rows;
    }
}
