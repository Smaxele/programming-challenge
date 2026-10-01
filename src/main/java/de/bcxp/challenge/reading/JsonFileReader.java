package de.bcxp.challenge.reading;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFileReader implements DataReader {
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
         try (Reader reader = new FileReader(file)) {
              JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
              JsonObject first = jsonArray.get(0).getAsJsonObject();
              rows.add(first.keySet().toArray(new String[0]));
              for (JsonElement element : jsonArray) {
                  JsonObject obj = element.getAsJsonObject();
                  rows.add(obj.entrySet().stream()
                      .map(e -> e.getValue().getAsString())
                      .toArray(String[]::new));
              }
          } catch (IOException e) {
              throw new IllegalArgumentException("Could not read file: " + filePath, e);
          }

        return rows;
    }
}
