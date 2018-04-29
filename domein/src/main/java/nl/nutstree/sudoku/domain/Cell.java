package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class Cell {

    private SudokuSize size;
    private Optional<Integer> value = Optional.empty();

    public Cell(SudokuSize size, Optional<Integer> value) {
        Validate.notNull(size);
        value.ifPresent(v -> validateValue(v, size));
        this.size = size;
        this.value = value;
    }

    public void setValue(int value) {
        Validate.isTrue(!this.value.isPresent(), "value already set");
        validateValue(value, size);
        this.value = Optional.of(value);
    }

    public Optional<Integer> getValue() {
        return value;
    }

    private static void validateValue(int value, SudokuSize size) {
        Validate.isTrue(value > 0 && value <= size.getValue(), "value '%s' should be smaller or equal then %s and bigger then 0", value, size.getValue());
    }

    @Override
    public String toString() {
        return value.map(v -> String.format("{%s}", v)).orElse("{ }");
    }
}
