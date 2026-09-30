package de.bcxp.challenge.reading;

import java.util.List;

public interface DataReader {
    List<String[]> read(String filePath);
}