package com.example.todoapp.control;

import java.util.Comparator;
import java.util.Objects;

public class TodoComparators {
    /**
     * Compares Comparables including null values. In results null values appear before manifested values.
     */
    public static final Comparator<Comparable> NULL_SAFE_COMPARATOR = (Comparable o1, Comparable o2) -> Objects.equals(o1, o2) ? 0 :
            (Objects.isNull(o1) ? -1 : (Objects.isNull(o2) ? +1 : o1.compareTo(o2)));
}
