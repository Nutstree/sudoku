package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class Cell {

    static final String INVALID_VALUE = "Invalid value: ";
    static final String INVALID_POSSIBILITY = "Invalid possibility: ";
    private Integer value;
    private Position position;
    private Set<Integer> possibilities;

    public Cell(Position position) {
        this.position = position;
        this.possibilities = new HashSet<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

    Cell(Set<Integer> possibilities, Position position) {
        validatePossibilities(possibilities);
        this.position = position;
        this.possibilities = possibilities;
    }

    public Cell(int value, Position position) {
        validateValue(value);
        this.value = value;
        this.position = position;
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

    private void validatePossibilities(Set<Integer> values) {
        values.stream()
            .forEach(i -> Validate.inclusiveBetween(1, 9, i, INVALID_POSSIBILITY + values));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(value, cell.value) &&
                Objects.equals(position, cell.position) &&
                Objects.equals(possibilities, cell.possibilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, position, possibilities);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", position=" + position +
                ", possibilities=" + possibilities +
                '}';
    }
}
