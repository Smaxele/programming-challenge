package de.bcxp.challenge.parsing;

import java.util.List;

public interface Parser<T> {
      List<T> parse(List<String[]> rows);
}