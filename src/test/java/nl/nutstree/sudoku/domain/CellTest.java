package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    @Test
    public void emptyConstructorMethod() {
        Cell emptyCell = Cell.empty();

        assertCellEmpty(emptyCell);
    }

    private void assertCellEmpty(Cell emptyCell) {
        assertThat(emptyCell.getValue()).isEmpty();
        assertThat(emptyCell.getPossibilities()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void negativeValue() {
        assertThatThrownBy(() -> Cell.of(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void zeroValue() {
        assertThatThrownBy(() -> Cell.of(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void tooBigValue() {
        assertThatThrownBy(() -> Cell.of(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validValues() {
        IntStream.rangeClosed(1, 9)
                .forEach(i -> assertThatCellContainsValue(Cell.of(i), i));

    }

    private void assertThatCellContainsValue(Cell cell, int value) {
        assertThat(cell.getValue()).contains(value);
        assertThat(cell.getPossibilities()).isEmpty();
    }
}