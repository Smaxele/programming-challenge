package de.bcxp.challenge.analysis;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Analyzer<T> {
    public T findBy(List<T> items, Comparator<T> comparator) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("No items given to compare");
        }

        return Collections.min(items, comparator);
  }
}