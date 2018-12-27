package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    private Location dummylocation;

    @BeforeEach
    public void setUp() {
        dummylocation = dummylocation.of(1, 1);
    }

    @Test
    public void emptyCreation() {
        Cell emptyCell = createEmptyCell();
        Set<Integer> expectedPossibilities = Cell.getDefaultPossibilities(9);

        Set<Integer> result = emptyCell.getPossibilities();

        assertThat(result).isEqualTo(expectedPossibilities);
    }

    @Test
    public void defaultPossibilities() {
        Set<Integer> result = Cell.getDefaultPossibilities(9);

        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void negativeValue() {
        assertThatThrownBy(() -> createCellWithValue(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void zeroValue() {
        assertThatThrownBy(() -> createCellWithValue(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void tooBigValue() {
        assertThatThrownBy(() -> createCellWithValue(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validValues() {
        IntStream.rangeClosed(1, 9)
                .forEach(this::assertValueCreatesValidCell);

    }

    private void assertValueCreatesValidCell(int value) {
        Cell result = createCellWithValue(value);

        assertThat(result.getValue()).contains(value);
        assertThat(result.getPossibilities()).isEmpty();
    }

    private Cell createEmptyCell() {
        return new Cell.Builder()
                .location(dummylocation)
                .build();
    }

    private Cell createCellWithValue(int value) {
        return new Cell.Builder()
                .value(value)
                .location(dummylocation)
                .build();
    }
}