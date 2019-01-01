package nl.nutstree.sudoku.domain.autovalue;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.Optional;

public enum Type {
    SQUARE_4X4(4, ImmutableSet.of(1, 2, 3, 4)),
    SQUARE_9X9(9, ImmutableSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

    public static final String INVALID_PUZZLE = "Puzzle string not valid!";
    private final int size;
    private final ImmutableSet<Integer> validValues;

    Type(int size, ImmutableSet<Integer> validValues) {
        this.size = size;
        this.validValues = validValues;
    }

    public static Type valueOf(int size) {
        Optional<Type> type = Arrays.stream(Type.values())
                .filter(t -> t.size == size)
                .findFirst();

        return type.orElseThrow(() -> new IllegalArgumentException(INVALID_PUZZLE));

    }

    public int getSize() {
        return size;
    }

    public ImmutableSet<Integer> getValidValues() {
        return validValues;
    }
}
