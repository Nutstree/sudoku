package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Cell emptyCell = Cell.empty();

        assertCellEmpty(emptyCell);
    }

    private void assertCellEmpty(Cell emptyCell) {
        assertThat(emptyCell.getValue()).isEqualTo(0);
        assertThat(emptyCell.getPossibilities()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }


    @Test
    public void negativeValue() {
        assertThatThrownBy(() -> createCellWithValue(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void zeroValue_isEmptyCell() {
        Cell result = createCellWithValue(0);

        assertCellEmpty(result);
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

        assertThat(result.getValue()).isEqualTo(value);
        assertThat(result.getPossibilities()).isEmpty();
    }

    private Cell createCellWithValue(int value) {
        return new Cell.Builder()
                .value(value)
                .build();
    }
}