package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class Cell {

    public static final String INVALID_VALUE = "Invalid value: ";
    private Integer value;
    private Set<Integer> possibilities;

    public Cell() {
        this.possibilities = new HashSet<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

    public Cell(int value) {
        validateValue(value);
        this.value = value;
        this.possibilities = Collections.emptySet();
    }

    public Collection<Integer> getPossibilities() {
        return possibilities;
    }

    public Optional<Integer> getValue() {
        return Optional.ofNullable(value);
    }

    public void removePossibility(int value) {
        validateValue(value);

        possibilities.remove(value);
    }

    private void validateValue(int value) {
        Validate.inclusiveBetween(1, 9, value, INVALID_VALUE + value);
    }
}
